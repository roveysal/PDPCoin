package org.example.service;

import org.example.modul.User;

import java.util.List;

public interface UserService{
    User create(User user);
    User get(long chatId);
    List<User> getAll();
    void update(long chatId, User user);
    void delete(long chatId);
}
