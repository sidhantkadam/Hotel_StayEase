package com.sid.project.StayEase.controller;

import com.sid.project.StayEase.dto.LoginRequest;
import com.sid.project.StayEase.dto.ResponseDTO;
import com.sid.project.StayEase.entity.User;
import com.sid.project.StayEase.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@Tag(name = "Auth APIs" ,description = "Login and Register")
public class AuthController {
    @Autowired
    private UserService userService;

    @Operation(description = "register new user/admin")
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody User user) {
        ResponseDTO register = userService.register(user);
        return ResponseEntity.status(register.getStatusCode()).body(register);
    }

    @Operation(description = "login existing user")
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestBody LoginRequest loginRequest) {
        ResponseDTO login = userService.login(loginRequest);
        return ResponseEntity.status(login.getStatusCode()).body(login);
    }
}
