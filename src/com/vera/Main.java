package com.vera;

import com.vera.dao.UsersDAO;
import com.vera.dto.UsersDTO;
import com.vera.init.InicializarDB;

import java.lang.reflect.Array;
import java.util.*;

public class Main {
    public static void main(String[] args)
    {
        System.out.println("Executing random user inserts");
        insertRandomUsers();

        System.out.println("Modifying random users");
        modifyUsers();

        System.out.println("Removing older modificated users");
        removeLessModificatedUsers();
    }

    private static void insertRandomUsers()
    {
        UsersDAO dao = new UsersDAO();

        for (int i = 0; i < 5; i++)
        {
            UsersDTO dto = new UsersDTO();

            dao.insertIntoDatabase(dto);
        }
    }

    private static void modifyUsers()
    {
        ArrayList<UsersDTO> listUsersDTO = new UsersDAO().getUsersFromDatabase();
        HashMap<UsersDTO, UsersDTO> listUsersToChange = new HashMap<>();
        UsersDAO dao = new UsersDAO();
        Random rnd = new Random();

        while(listUsersToChange.size() < 5)
        {
            int position = rnd.nextInt(listUsersDTO.size());
            UsersDTO userToChange = listUsersDTO.get(position);

            if(!listUsersToChange.containsKey(userToChange))
            {
                listUsersToChange.put(userToChange, new UsersDTO());
            }
        }

        for (UsersDTO key : listUsersToChange.keySet())
        {
            System.out.println("Modfifying user: " + key.toString() + "New User: " + listUsersToChange.get(key).toString());
            dao.updateUsers(key, listUsersToChange.get(key));
        }
    }

    private static void removeLessModificatedUsers()
    {
        UsersDAO dao = new UsersDAO();
        dao.removeLessModificatedUsers();
    }
}