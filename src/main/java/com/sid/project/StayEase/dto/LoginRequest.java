package com.sid.project.StayEase.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest
{
    @NotBlank(message = "Email is required !")
    private String email;
    @NotBlank(message = "password is required !")
    private String password;

    public @NotBlank(message = "Email is required !") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required !") String email) {
        this.email = email;
    }

    public @NotBlank(message = "password is required !") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "password is required !") String password) {
        this.password = password;
    }
}
