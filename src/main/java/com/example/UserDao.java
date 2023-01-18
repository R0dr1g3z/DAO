package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

public class UserDao {
    private static final String CREATE_USER_QUERY = "INSERT INTO users(email,username,password) VALUES(?,?,?)";
    private static final String READ_USER_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET email = ?, username = ?, password = ? WHERE id = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String FINDALL_USER_QUERY = "SELECT * FROM users";

    public void create(User user) {
        try (Connection conn = DbUtil.connect("DAO")) {
            PreparedStatement statement = conn.prepareStatement(CREATE_USER_QUERY);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUsername());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User read(int userId) throws SQLException {
        try (Connection connection = DbUtil.connect("DAO")) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_USER_QUERY);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String email = resultSet.getString(2);
                String username = resultSet.getString(3);
                String password = resultSet.getString(4);
                User user = new User(email, username, password);
                return user;
            }
            return null;
        }
    }

    public void update(User user, int userId) throws SQLException {
        try (Connection connection = DbUtil.connect("DAO")) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, hashPassword(user.getPassword()));
            preparedStatement.setInt(4, userId);
            preparedStatement.executeUpdate();
        }
    }
    public void delete(int userId) throws SQLException{
        try(Connection connection = DbUtil.connect("DAO")){
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_QUERY);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        }
    }
    public List<User> findAll() throws SQLException{
        List<User> users = new ArrayList<>();
        try(Connection connection = DbUtil.connect("DAO")){
            PreparedStatement preparedStatement = connection.prepareStatement(FINDALL_USER_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt(1);
                String email = resultSet.getString(2);
                String username = resultSet.getString(3);
                String password = resultSet.getString(4);
                User user = new User(id, email, username, password);
                users.add(user);
            }
        }
        return users;
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
