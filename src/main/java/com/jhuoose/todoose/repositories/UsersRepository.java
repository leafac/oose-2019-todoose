package com.jhuoose.todoose.repositories;

import com.jhuoose.todoose.models.User;

import java.sql.Connection;
import java.sql.SQLException;

public class UsersRepository {
    private Connection connection;

    public UsersRepository(Connection connection) throws SQLException {
        this.connection = connection;
        var statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS users (login TEXT PRIMARY KEY, password TEXT)");
        statement.close();
    }

    public User getOne(String login) throws SQLException, UserNotFoundException {
        var statement = connection.prepareStatement("SELECT login, password FROM users WHERE login = ?");
        statement.setString(1, login);
        var result = statement.executeQuery();
        try {
            if (result.next()) {
                return new User(
                        result.getString("login"),
                        result.getString("password")
                );
            } else {
                throw new UserNotFoundException();
            }
        }
        finally {
            statement.close();
            result.close();
        }
    }

    public void create(User user) throws SQLException {
        var statement = connection.prepareStatement("INSERT INTO users (login, password) VALUES (?, ?)");
        statement.setString(1, user.getLogin());
        statement.setString(2, user.getPassword());
        statement.execute();
        statement.close();
    }
}
