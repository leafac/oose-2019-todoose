package com.jhuoose.todoose;

import com.jhuoose.todoose.controllers.ItemsController;
import com.jhuoose.todoose.controllers.UsersController;
import com.jhuoose.todoose.repositories.ItemNotFoundException;
import com.jhuoose.todoose.repositories.ItemsRepository;
import com.jhuoose.todoose.repositories.UsersRepository;
import io.javalin.Javalin;

import java.sql.DriverManager;
import java.sql.SQLException;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Server {
    public static void main(String[] args) throws SQLException {
        var connection = DriverManager.getConnection("jdbc:sqlite:todoose.db");
        var usersRepository = new UsersRepository(connection);
        var itemsRepository = new ItemsRepository(connection);
        var usersController = new UsersController(usersRepository);
        var itemsController = new ItemsController(usersController, itemsRepository);
        Javalin.create(config -> { config.addStaticFiles("/public"); })
        .events(event -> {
            event.serverStopped(() -> { connection.close(); });
        })
        .routes(() -> {
            path("items", () -> {
                get(itemsController::getAll);
                post(itemsController::create);
                path(":identifier", () -> {
                    delete(itemsController::delete);
                    put(itemsController::update);
                });
            });
            path("users", () -> {
                post(usersController::signup);
                path("login", () -> {
                    post(usersController::login);
                });
            });

        })
        .exception(ItemNotFoundException.class, (e, ctx) -> { ctx.status(404); })
        .start(System.getenv("PORT") == null ? 7000 : Integer.parseInt(System.getenv("PORT")));
    }
}
