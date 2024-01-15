package com.vera.test;

import com.vera.init.InicializarDB;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Init_Test
{
    InicializarDB db;

    @Before
    public void startConnection()
    {
        db = new InicializarDB("root", "root");
    }

    /* Database created correctly*/
    @Test
    public void testSchema()
    {
        assertTrue(db.testSchema());

        db = new InicializarDB("wrong_username", "wrong_password");
        assertFalse(db.testSchema());
    }

    /* Test closing database */
    @Test
    public void testClose()
    {
        assertTrue(db.close());

        db = new InicializarDB("wrong_username", "wrong_password");
        assertFalse(db.close());
    }

    /* Test default users and table creation */
    @Test
    public void testCrearUsuarios()
    {
        assertTrue(db.crearUsuarios());
        assertTrue(db.testUsuarios() >= 20);
    }
}
