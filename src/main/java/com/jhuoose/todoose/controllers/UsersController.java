package com.jhuoose.todoose.controllers;

import com.jhuoose.todoose.models.User;
import com.jhuoose.todoose.repositories.UserNotFoundException;
import com.jhuoose.todoose.repositories.UsersRepository;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;

import java.sql.SQLException;

public class UsersController {
    private UsersRepository usersRepository;

    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void signup(Context ctx) throws SQLException {
        usersRepository.create(new User(ctx.formParam("login", ""), ctx.formParam("password", "")));
        ctx.status(201);
    }

    public void login(Context ctx) throws SQLException, UserNotFoundException {
        var user = usersRepository.getOne(ctx.formParam("login", ""));
        if (!user.getPassword().equals(ctx.formParam("password", ""))) {
            throw new ForbiddenResponse();
        }
        ctx.sessionAttribute("user", user);
        ctx.status(200);
    }

    public User currentUser(Context ctx) {
        var user = (User) ctx.sessionAttribute("user");
        if (user == null) throw new ForbiddenResponse();
        return user;
    }
}
