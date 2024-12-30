package com.sid.project.StayEase.controller;

import com.sid.project.StayEase.dto.LoginRequest;
import com.sid.project.StayEase.dto.ResponseDTO;
import com.sid.project.StayEase.entity.User;
import com.sid.project.StayEase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody User user) {
        ResponseDTO register = userService.register(user);
        return ResponseEntity.status(register.getStatusCode()).body(register);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(LoginRequest loginRequest) {
        ResponseDTO login = userService.login(loginRequest);
        return ResponseEntity.status(login.getStatusCode()).body(login);
    }
}
