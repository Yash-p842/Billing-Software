package com.yashporwal.BillingSoftware.service.impl;

import com.yashporwal.BillingSoftware.entity.UserEntity;
import com.yashporwal.BillingSoftware.io.UserRequest;
import com.yashporwal.BillingSoftware.io.UserResponse;
import com.yashporwal.BillingSoftware.repository.UserRepo;
import com.yashporwal.BillingSoftware.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserResponse createUser(UserRequest request) {
        UserEntity newUser = convertToEntity(request);
        newUser = userRepo.save(newUser);
        return convertToResponse(newUser);
    }

    private UserResponse convertToResponse(UserEntity newUser) {
        return UserResponse.builder()
                .userId(newUser.getUserId())
                .name(newUser.getName())
                .email(newUser.getEmail())
                .role(newUser.getRole())
                .createdAt(newUser.getCreatedAt())
                .updatedAt(newUser.getUpdatedAt())
                .build();

    }

    private UserEntity convertToEntity(UserRequest request) {
        return UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .name(request.getName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(request.getRole().toUpperCase())
                .build();

    }

    @Override
    public List<UserResponse> readUsers() {
        return userRepo.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public String getUserRole(String email) {
        UserEntity existingUser = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found for the email: "+email));
        return existingUser.getRole();

    }

    @Override
    public void deleteUser(String userId) {
        UserEntity existingUser = userRepo.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found for the userId: "+userId));
        userRepo.delete(existingUser);
    }
}
