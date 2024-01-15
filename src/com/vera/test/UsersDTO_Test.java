package com.vera.test;

import com.vera.dto.UsersDTO;
import com.vera.init.InicializarDB;
import org.junit.*;

public class UsersDTO_Test
{
    InicializarDB db;

    @Before
    public void startConnection()
    {
        db = new InicializarDB("root", "root");
    }

    @Test
    public void testRandomGenerator()
    {
        for (int i = 0; i < 5000; i++)
        {
            System.out.println(new UsersDTO().toString());
        }

        System.out.println(new UsersDTO("Test1", "Test2", "Test3@test.com", "Test1").toString());
    }
}
