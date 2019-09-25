package com.jhuoose.todoose;

import com.jhuoose.todoose.controllers.ItemsController;
import com.jhuoose.todoose.repositories.ItemNotFoundException;
import com.jhuoose.todoose.repositories.ItemsRepository;
import io.javalin.Javalin;

import java.sql.DriverManager;
import java.sql.SQLException;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Server {
    public static void main(String[] args) throws SQLException {
        var connection = DriverManager.getConnection("jdbc:sqlite:todoose.db");
        var itemsRepository = new ItemsRepository(connection);
        var itemsController = new ItemsController(itemsRepository);
        Javalin.create(config -> { config.addStaticFiles("/public"); })
        .events(event -> {
            event.serverStopped(() -> { connection.close(); });
        })
        .routes(() -> {
            path("items", () -> {
                get(itemsController::getAll);
                post(itemsController::create);
                path(":identifier", () -> {
                    // TODO: Change the documentation of ‘Mark items as done’ to:
                    //  1. Use the appropriate method, as discussed in lecture.
                    //  2. Pass the appropriate field: ‘completed’.
                    //  3. Update the example accordingly.
                    put(itemsController::update);
                });
            });
        })
        .exception(ItemNotFoundException.class, (e, ctx) -> { ctx.status(404); })
        .start(System.getenv("PORT") == null ? 7000 : Integer.parseInt(System.getenv("PORT")));
    }
}
