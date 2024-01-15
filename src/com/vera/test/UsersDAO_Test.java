package com.vera.test;

import com.vera.init.InicializarDB;
import org.junit.*;

import static org.junit.Assert.*;
public class UsersDAO_Test
{
    InicializarDB db;

    @Before
    public void startConnection()
    {
        db = new InicializarDB("root", "root");
    }
}
