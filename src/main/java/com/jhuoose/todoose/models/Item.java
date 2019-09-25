package com.jhuoose.todoose.models;

public class Item {
    private int identifier;
    private String description;
    private boolean completed;

    public Item(int identifier, String description, boolean completed) {
        this.identifier = identifier;
        this.description = description;
        this.completed = completed;
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
