package com.jhuoose.todoose.controllers;

import com.jhuoose.todoose.repositories.ItemNotFoundException;
import com.jhuoose.todoose.repositories.ItemsRepository;
import io.javalin.http.Context;

import java.sql.SQLException;

public class ItemsController {
    private ItemsRepository itemsRepository;

    public ItemsController(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    public void getAll(Context ctx) throws SQLException {
        ctx.json(itemsRepository.getAll());
    }

    public void create(Context ctx) throws SQLException {
        itemsRepository.create();
        ctx.status(201);
    }

    public void delete(Context ctx) throws SQLException, ItemNotFoundException {
        itemsRepository.delete(itemsRepository.getOne(Integer.parseInt(ctx.pathParam("identifier"))));
        ctx.status(204);
    }

    public void update(Context ctx) throws SQLException, ItemNotFoundException {
        var item = itemsRepository.getOne(Integer.parseInt(ctx.pathParam("identifier")));
        item.setDescription(ctx.formParam("description", ""));
        itemsRepository.update(item);
        ctx.status(204);
    }
}
