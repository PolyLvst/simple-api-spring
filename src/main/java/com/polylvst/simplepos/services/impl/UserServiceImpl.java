package com.polylvst.simplepos.services.impl;

import com.polylvst.simplepos.domain.Role;
import com.polylvst.simplepos.domain.entities.User;
import com.polylvst.simplepos.repositories.UserRepository;
import com.polylvst.simplepos.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);
        user.setRole(Role.KASIR);
        return userRepository.save(user);
    }

    @Override
    public User findUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id "+id));
    }

    @Override
    public User activateUser(UUID id, UUID userId) {
        User activatedUser = findUserById(id);
        activatedUser.setActive(true);
        activatedUser.setUpdatedBy(userId);
        userRepository.save(activatedUser);
        return activatedUser;
    }
}
