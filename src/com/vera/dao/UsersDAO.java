package com.vera.dao;

import com.vera.dto.UsersDTO;
import com.vera.init.InicializarDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsersDAO
{
    private Connection sqlConnection;
    private InicializarDB database;

    public void insertIntoDatabase(UsersDTO user)
    {
        this.init();

        try
        {
            PreparedStatement st = sqlConnection.prepareStatement("INSERT INTO Usuarios(full_name, user, email, password) VALUES(?, ?, ?, ?)");
            st.setString(1, user.getFull_name());
            st.setString(2, user.getUser());
            st.setString(3, user.getEmail());
            st.setString(4, user.getPassword());

            st.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error al conectar a la base de datos: " + e);
        }
        finally
        {
            this.database.close();
        }
    }

    public ArrayList<UsersDTO> getUsersFromDatabase()
    {
        this.init();
        ArrayList<UsersDTO> result = new ArrayList<>();

        try
        {
            PreparedStatement st = sqlConnection.prepareStatement("SELECT * FROM Usuarios");
            ResultSet data = st.executeQuery();

            while(data.next())
            {
                result.add(new UsersDTO(data.getString("full_name"), data.getString("user"), data.getString("email"), data.getString("password")));
            }

            return result;
        }
        catch (SQLException e)
        {
            System.out.println("Error al conectar a la base de datos: " + e);
        }
        finally
        {
            this.database.close();
        }

        return result;
    }

    public void updateUsers(UsersDTO userTochange, UsersDTO newUser)
    {
        this.init();

        try
        {
            PreparedStatement st = sqlConnection.prepareStatement("UPDATE Usuarios SET full_name = ?, user = ?, email = ?, password = ? WHERE user = ?");
            st.setString(1, newUser.getFull_name());
            st.setString(2, newUser.getUser());
            st.setString(3, newUser.getEmail());
            st.setString(4, newUser.getPassword());
            st.setString(5, userTochange.getUser());

            st.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Error al conectar a la base de datos: " + e);
        }
        finally
        {
            this.database.close();
        }
    }

    public void removeLessModificatedUsers()
    {
        this.init();

        try
        {
            PreparedStatement st = sqlConnection.prepareStatement("SELECT * FROM Usuarios WHERE modification_date IS NOT NULL ORDER BY modification_date LIMIT 5");
            PreparedStatement st2 = sqlConnection.prepareStatement("DELETE FROM Usuarios WHERE user = ?");
            ResultSet set = st.executeQuery();

            while(set.next())
            {
                st2.setString(1, set.getString("user"));
                st2.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error al conectar a la base de datos: " + e);
        }
        finally
        {
            this.database.close();
        }
    }

    private void init()
    {
        this.database = new InicializarDB("root", "root");

        if(!database.testSchema())
        {
            database.crearUsuarios();
            this.database = new InicializarDB("root", "root");
        }

        this.sqlConnection = database.getSqlConnection();
    }
}
