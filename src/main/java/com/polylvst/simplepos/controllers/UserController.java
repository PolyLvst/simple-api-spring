package com.polylvst.simplepos.controllers;

import com.polylvst.simplepos.domain.dtos.CreateUserRequest;
import com.polylvst.simplepos.domain.dtos.UserDto;
import com.polylvst.simplepos.domain.entities.User;
import com.polylvst.simplepos.mappers.UserMapper;
import com.polylvst.simplepos.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> listUsers() {
        List<UserDto> users = userService.listUsers()
                .stream().map(user -> userMapper.toDto(user))
                .toList();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(
            @Valid
            @RequestBody
            CreateUserRequest createUserRequest
    ) {
        User userToCreate = userMapper.toEntity(createUserRequest);
        User savedUser = userService.createUser(userToCreate);
        return new ResponseEntity<>(
                userMapper.toDto(savedUser),
                HttpStatus.CREATED
        );
    }
}
