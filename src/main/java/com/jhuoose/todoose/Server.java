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
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
        });
        var connection = DriverManager.getConnection("jdbc:sqlite:todoose.db");
        var itemsRepository = new ItemsRepository(connection);
        var itemsController = new ItemsController(itemsRepository);
        app.events(event -> {
            event.serverStopped(() -> {
                connection.close();
            });
        });
        app.routes(() -> {
            path("items", () -> {
                get(itemsController::getAll);
                post(itemsController::create);
                path(":identifier", () -> {
                    delete(itemsController::delete);
                    put(itemsController::update);
                });
            });
        });
        app.exception(ItemNotFoundException.class, (e, ctx) -> { ctx.status(404); });
        app.start(System.getenv("PORT") == null ? 7000 : Integer.parseInt(System.getenv("PORT")));
    }
}
