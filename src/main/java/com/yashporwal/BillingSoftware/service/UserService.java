package com.yashporwal.BillingSoftware.service;

import com.yashporwal.BillingSoftware.io.UserRequest;
import com.yashporwal.BillingSoftware.io.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request);

    List<UserResponse> readUsers();

    String getUserRole(String email);

    void deleteUser(String userId);
}
