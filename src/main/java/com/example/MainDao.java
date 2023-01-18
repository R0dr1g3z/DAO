package com.example;

import java.sql.SQLException;

public class MainDao {
    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDao();
        // User user = new User("bataty@gmail.com", "Batatek256", "BatatoweNorki23");
        // userDao.create(user);
        // User user2 = userDao.read(1);
        // System.out.println(user2.getPassword());
        // User user3 = new User("Kasztanek@gmail.com", "Kasztanowy50","Kasztanki5555");
        // userDao.update(user3, 1);
        // userDao.delete(5);
        for (User user : userDao.findAll()) {
            System.out.println(
                    user.getId() + " " + user.getEmail() + " " + user.getUsername() + " " + user.getPassword());
        }
    }
}
