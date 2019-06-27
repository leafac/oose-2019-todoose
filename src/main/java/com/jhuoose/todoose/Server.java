package com.jhuoose.todoose;

import com.jhuoose.todoose.models.Item;
import io.javalin.Javalin;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws SQLException {
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
        });
        var connection = DriverManager.getConnection("jdbc:sqlite:todoose.db");
        app.events(event -> {
            event.serverStarting(() -> {
                var statement = connection.createStatement();
                statement.execute("CREATE TABLE IF NOT EXISTS items (identifier INTEGER PRIMARY KEY AUTOINCREMENT, description TEXT)");
                statement.close();
            });
            event.serverStopped(() -> {
                connection.close();
            });
        });
        app.get("/items", ctx -> {
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
            ctx.json(items);
        });
        app.post("/items", ctx -> {
            var statement = connection.createStatement();
            statement.execute("INSERT INTO items (description) VALUES (\"\")");
            statement.close();
            ctx.status(201);
        });
        app.delete("/items/:identifier", ctx -> {
            var statement = connection.prepareStatement("DELETE FROM items WHERE identifier = ?");
            statement.setInt(1, Integer.parseInt(ctx.pathParam("identifier")));
            if (statement.executeUpdate() == 0) {
                ctx.status(404);
            } else {
                ctx.status(204);
            }
            statement.close();
        });
        app.put("/items/:identifier", ctx -> {
            var statement = connection.prepareStatement("UPDATE items SET description = ? WHERE identifier = ?");
            statement.setString(1, ctx.formParam("description", ""));
            statement.setInt(2, Integer.parseInt(ctx.pathParam("identifier")));
            if (statement.executeUpdate() == 0) {
                ctx.status(404);
            } else {
                ctx.status(204);
            }
            statement.close();
        });
        app.start(System.getenv("PORT") == null ? 7000 : Integer.parseInt(System.getenv("PORT")));
    }
}
