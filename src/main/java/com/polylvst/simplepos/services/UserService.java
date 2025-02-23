package com.polylvst.simplepos.services;

import com.polylvst.simplepos.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> listUsers();
    User createUser(User user);
    User findUserById(UUID id);
    User activateUser(UUID id, UUID userId);
    void deleteUser(UUID id);
}
