package com.polylvst.simplepos.services.impl;

import com.polylvst.simplepos.domain.Role;
import com.polylvst.simplepos.domain.entities.User;
import com.polylvst.simplepos.repositories.UserRepository;
import com.polylvst.simplepos.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User createUser(User user) {
        boolean isExist = userRepository.existsByUsernameIgnoreCase(user.getUsername());
        if (isExist) {
            throw new IllegalArgumentException("Username already existed with name : "+ user.getUsername());
        }
        user.setActive(false);
        user.setRole(Role.KASIR);
        return userRepository.save(user);
    }
}
