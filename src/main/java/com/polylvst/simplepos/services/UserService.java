package com.polylvst.simplepos.services;

import com.polylvst.simplepos.domain.entities.User;

import java.util.List;

public interface UserService {
    List<User> listUsers();
    User createUser(User user);
}
