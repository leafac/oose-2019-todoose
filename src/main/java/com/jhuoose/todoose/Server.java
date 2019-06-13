package com.jhuoose.todoose;

import io.javalin.Javalin;
import io.javalin.staticfiles.Location;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) throws SQLException {
        Javalin app = Javalin.create();
        var connection = DriverManager.getConnection("jdbc:sqlite:todoose.db");
        var statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS items (identifier INTEGER PRIMARY KEY AUTOINCREMENT, description TEXT)");
        var items = new ArrayList<Item>();
        app.get("/items", ctx -> {
            ctx.json(items);
        });
        app.post("/items", ctx -> {
            items.add(new Item());
            ctx.status(201);
        });
        app.enableStaticFiles("/public");
        app.start(7000);
    }
}

class Item {
    private int identifier;
    private String description;
    private static int currentIdentifier = 0;

    public Item() {
        this.identifier = currentIdentifier++;
        this.description = "";
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
