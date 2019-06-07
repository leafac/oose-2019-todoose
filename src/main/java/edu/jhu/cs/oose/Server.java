package edu.jhu.cs.oose;

import io.javalin.Javalin;

import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);
        var items = List.of(
                new Item(1, "Read TODO items"),
                new Item(2, "Create new TODO items"),
                new Item(3, "Edit TODO item"),
                new Item(4, "Mark items as DONE")
        );
        app.get("/items", ctx -> {
            ctx.json(items);
        });
    }
}

class Item {
    int identifier;
    String description;

    public Item(int identifier, String description) {
        this.identifier = identifier;
        this.description = description;
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
