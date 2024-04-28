package org.example.service;

import org.example.modul.User;
import org.example.repository.UserRepository;

import java.util.List;

public class UserServiceImpl implements UserService{
    private final UserRepository userRepository = new UserRepository();
    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public User get(long chatId) {
        return userRepository.getUserById(chatId);
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void update(long chatId, User user) {

    }

    @Override
    public void delete(long chatId) {

    }
}
