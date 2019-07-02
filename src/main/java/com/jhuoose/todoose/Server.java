package com.jhuoose.todoose;

import com.jhuoose.todoose.repositories.ItemNotFoundException;
import com.jhuoose.todoose.repositories.ItemsRepository;
import io.javalin.Javalin;

import java.sql.DriverManager;
import java.sql.SQLException;

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
        app.get("/items", ctx -> { ctx.json(itemsRepository.getAll()); });
        app.post("/items", ctx -> {
            itemsRepository.create();
            ctx.status(201);
        });
        app.delete("/items/:identifier", ctx -> {
            itemsRepository.delete(itemsRepository.getOne(Integer.parseInt(ctx.pathParam("identifier"))));
            ctx.status(204);
        });
        app.put("/items/:identifier", ctx -> {
            var item = itemsRepository.getOne(Integer.parseInt(ctx.pathParam("identifier")));
            item.setDescription(ctx.formParam("description", ""));
            itemsRepository.update(item);
            ctx.status(204);
        });
        app.exception(ItemNotFoundException.class, (e, ctx) -> { ctx.status(404); });
        app.start(System.getenv("PORT") == null ? 7000 : Integer.parseInt(System.getenv("PORT")));
    }
}
