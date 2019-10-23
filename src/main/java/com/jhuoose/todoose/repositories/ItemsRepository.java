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

    public List<Item> getAll() throws SQLException {
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

    public Item getOne(int identifier) throws SQLException, ItemNotFoundException {
        var statement = connection.prepareStatement("SELECT identifier, description FROM items WHERE identifier = ?");
        statement.setInt(1, identifier);
        var result = statement.executeQuery();
        try {
            if (result.next()) {
                return new Item(
                        result.getInt("identifier"),
                        result.getString("description")
                );
            } else {
                throw new ItemNotFoundException();
            }
        }
        finally {
            statement.close();
            result.close();
        }
    }

    public void create() throws SQLException {
        var statement = connection.createStatement();
        statement.execute("INSERT INTO items (description) VALUES (\"\")");
        statement.close();
    }

    public void update(Item item) throws SQLException, ItemNotFoundException {
        var statement = connection.prepareStatement("UPDATE items SET description = \"" + item.getDescription() + "\" WHERE identifier = " + item.getIdentifier());
        try {
            if (statement.executeUpdate() == 0) throw new ItemNotFoundException();
        }
        finally {
            statement.close();
        }
    }

    public void delete(Item item) throws SQLException, ItemNotFoundException {
        var statement = connection.prepareStatement("DELETE FROM items WHERE identifier = ?");
        statement.setInt(1, item.getIdentifier());
        try {
            if (statement.executeUpdate() == 0) throw new ItemNotFoundException();
        }
        finally {
            statement.close();
        }
    }
}
