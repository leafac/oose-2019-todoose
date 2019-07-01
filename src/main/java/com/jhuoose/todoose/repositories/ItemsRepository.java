package com.jhuoose.todoose.repositories;

import com.jhuoose.todoose.models.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemsRepository {
    private Connection connection;

    public ItemsRepository(Connection connection) throws SQLException {
        this.connection = connection;
        var statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS items (identifier INTEGER PRIMARY KEY AUTOINCREMENT, description TEXT)");
        statement.close();
    }

    public List<Item> getItems() throws SQLException {
        var items = new ArrayList<Item>();
        var statement = connection.createStatement();
        var result = statement.executeQuery("SELECT identifier, description FROM items");
        while (result.next()) {
            items.add(
                new Item(
                    result.getInt("identifier"),
                    result.getString("description")
                )
            );
        }
        result.close();
        statement.close();
        return items;
    }

    public void createItem() throws SQLException {
        var statement = connection.createStatement();
        statement.execute("INSERT INTO items (description) VALUES (\"\")");
        statement.close();
    }

    public boolean deleteItem(int identifier) throws SQLException {
        var statement = connection.prepareStatement("DELETE FROM items WHERE identifier = ?");
        statement.setInt(1, identifier);
        var isDeleted = statement.executeUpdate() == 1;
        statement.close();
        return isDeleted;
    }

    public boolean editItem(int identifier, String description) throws SQLException {
        var statement = connection.prepareStatement("UPDATE items SET description = ? WHERE identifier = ?");
        statement.setString(1, description);
        statement.setInt(2, identifier);
        var isEdited = statement.executeUpdate() == 1;
        statement.close();
        return isEdited;
    }
}
