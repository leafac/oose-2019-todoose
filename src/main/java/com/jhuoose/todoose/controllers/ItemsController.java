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

    public void update(Context ctx) throws SQLException, ItemNotFoundException {
        var item = itemsRepository.getOne(ctx.pathParam("identifier", Integer.class).get());
        var description = ctx.formParam("description", "");
        if (!description.isEmpty()) item.setDescription(description);
        var completed = ctx.formParam("completed", Boolean.class).getOrNull();
        if (completed != null) item.setCompleted(completed);
        itemsRepository.update(item);
        ctx.status(204);
    }
}
