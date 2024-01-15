package com.vera.init;

import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;

public class InicializarDB {
    protected Connection sqlConnection;
    protected String username;
    protected String password;

    /**
     * Creates a connection to the database
     *
     * @param username Username of the user
     * @param password Password of the user
     */
    public InicializarDB(String username, String password) {
        if (init("ut2", username, password)) {
            this.username = username;
            this.password = password;
        }
    }

    public Connection getSqlConnection() {
        return this.sqlConnection;
    }

    /**
     * Testing if the schema is created or not
     */
    public boolean testSchema()
    {
        if(sqlConnection != null)
        {
            try
            {
                PreparedStatement st = sqlConnection.prepareStatement("USE ut2");
                st.executeUpdate();

                return true;
            }
            catch (SQLException err)
            {
                System.out.println("Error al conectar a la base de datos: " + err);
                sqlConnection = null;
            }
        }

        return false;
    }

    /**
     * Returns the amount of actual users
     * <p>
     * If value == 20, test is correct
     */
    public int testUsuarios() {
        if (this.init("ut2")) {
            try {
                PreparedStatement st = sqlConnection.prepareStatement("SELECT COUNT(*) FROM Usuarios");
                ResultSet data = st.executeQuery();

                data.next();

                return data.getInt(1);
            } catch (SQLException error) {
                System.out.println("Error al conectar a la base de datos: " + error);
            }

            return -1;
        }

        return -1;
    }

    /**
     * Creates tables and inserts default users
     */
    public boolean crearUsuarios() {
        if (this.init("ut2")) {
            try {
                ScriptRunner sr = new ScriptRunner(sqlConnection);
                sr.setStopOnError(true);

                Reader reader = new BufferedReader(new FileReader("src/com/vera/init/src/Init_SQL.sql"));

                sr.runScript(reader);

                return true;
            } catch (FileNotFoundException e) {
                System.out.println("No se encontró el archivo SQL: " + e.getMessage());
            } catch (RuntimeSqlException error) {
                System.out.println("No se pudo cargar el archivo SQL: " + error.getMessage());
            } finally {
                this.close();
            }
        }

        return false;
    }

    /**
     * Closes the database
     */
    public boolean close() {
        if (sqlConnection != null) {
            try {
                sqlConnection.close();
                sqlConnection = null;
            } catch (SQLException e) {
                System.out.println("Error cerrando la base de datos: " + e);
                return false;
            }

            return true;
        }

        return false;
    }

    /**
     * Overcharged function: Initiates database with already stored credentials
     *
     * @param database Database used
     */
    private boolean init(String database)
    {
        return this.init(database, this.username, this.password);
    }

    /**
     * Initiates database. If database is not created, creates it
     *
     * @param database Database where we want the connection
     * @param username Username used in the connection
     * @param password Password used in the connection
     */
    private boolean init(String database, String username, String password)
    {
        try
        {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost/" + database, username, password);

            return true;
        }
        catch (NullPointerException e)
        {
            System.out.println("Error cargando el driver: " + e);
            sqlConnection = null;
        }
        catch (SQLException error)
        {
            if (error.getClass().getSimpleName().equals("SQLSyntaxErrorException"))
            {
                try
                {
                    sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost/", username, password);

                    PreparedStatement st = sqlConnection.prepareStatement("CREATE DATABASE " + database);
                    st.executeUpdate();

                    this.init(database, username, password);

                    return true;
                }
                catch (SQLException err)
                {
                    System.out.println("Error al conectar a la base de datos: " + err);
                    sqlConnection = null;
                }
                finally
                {
                    this.close();
                }
            }
            else
            {
                System.out.println("Error al conectar a la base de datos: " + error);
                sqlConnection = null;
            }
        }

        return false;
    }
}
