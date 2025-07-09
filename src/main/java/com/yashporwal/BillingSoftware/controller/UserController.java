package com.yashporwal.BillingSoftware.controller;

import com.yashporwal.BillingSoftware.io.UserRequest;
import com.yashporwal.BillingSoftware.io.UserResponse;
import com.yashporwal.BillingSoftware.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUser(@RequestBody UserRequest request){
        try {
            return userService.createUser(request);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create user"+e.getMessage());
        }
    }

    @GetMapping("/users")
    public List<UserResponse> fetchUsers(){
        return userService.readUsers();
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUser(@PathVariable("userId") String userId){
       try {
           userService.deleteUser(userId);
       } catch (Exception e) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with UserId: "+userId);
       }
    }


}
