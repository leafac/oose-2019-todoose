package com.jhuoose.todoose;

import io.javalin.Javalin;
import io.javalin.staticfiles.Location;

import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        Javalin app = Javalin.create();
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
        var list = List.of(1, 2, 3);
        list.forEach(number -> {
            System.out.println(number);
        });
        for (var number :  list) {
            System.out.println(number);
        }
        for (int i = 0; i < list.size(); i++) {
            var number = list.get(i);
            System.out.println(number);
        }
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
