package com.jhuoose.todoose.models;

public class Item {
    private int identifier;
    private String description;

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
