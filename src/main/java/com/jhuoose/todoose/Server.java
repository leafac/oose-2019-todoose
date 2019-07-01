package com.jhuoose.todoose;

import com.jhuoose.todoose.models.Item;
import com.jhuoose.todoose.repositories.ItemsRepository;
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
        var itemsRepository = new ItemsRepository(connection);
        app.events(event -> {
            event.serverStopped(() -> {
                connection.close();
            });
        });
        app.get("/items", ctx -> { ctx.json(itemsRepository.getItems()); });
        app.post("/items", ctx -> {
            itemsRepository.createItem();
            ctx.status(201);
        });
        app.delete("/items/:identifier", ctx -> {
            if (itemsRepository.deleteItem(Integer.parseInt(ctx.pathParam("identifier")))) {
                ctx.status(204);
            } else {
                ctx.status(404);
            }
        });
        app.put("/items/:identifier", ctx -> {
            if (itemsRepository.editItem(
                    Integer.parseInt(ctx.pathParam("identifier")),
                    ctx.formParam("description", ""))
            ) {
                ctx.status(204);
            } else {
                ctx.status(404);
            }
        });
        app.start(System.getenv("PORT") == null ? 7000 : Integer.parseInt(System.getenv("PORT")));
    }
}
