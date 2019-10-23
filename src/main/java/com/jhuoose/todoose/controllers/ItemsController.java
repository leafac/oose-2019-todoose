package com.jhuoose.todoose.controllers;

import com.jhuoose.todoose.repositories.ItemNotFoundException;
import com.jhuoose.todoose.repositories.ItemsRepository;
import io.javalin.http.Context;

import java.sql.SQLException;

public class ItemsController {
    private UsersController usersController;
    private ItemsRepository itemsRepository;

    public ItemsController(UsersController usersController, ItemsRepository itemsRepository) {
        this.usersController = usersController;
        this.itemsRepository = itemsRepository;
    }

    public void getAll(Context ctx) throws SQLException {
        var user = usersController.currentUser(ctx);
        ctx.json(itemsRepository.getAll());
    }

    public void create(Context ctx) throws SQLException {
        itemsRepository.create();
        ctx.status(201);
    }

    public void delete(Context ctx) throws SQLException, ItemNotFoundException {
        itemsRepository.delete(itemsRepository.getOne(ctx.pathParam("identifier", Integer.class).get()));
        ctx.status(204);
    }

    public void update(Context ctx) throws SQLException, ItemNotFoundException {
        var item = itemsRepository.getOne(ctx.pathParam("identifier", Integer.class).get());
        item.setDescription(ctx.formParam("description", ""));
        itemsRepository.update(item);
        ctx.status(204);
    }
}
