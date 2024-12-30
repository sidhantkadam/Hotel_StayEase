package com.sid.project.StayEase.service;

import com.sid.project.StayEase.dto.LoginRequest;
import com.sid.project.StayEase.dto.ResponseDTO;
import com.sid.project.StayEase.entity.User;


public interface UserService
{
    ResponseDTO register(User user);
    ResponseDTO login(LoginRequest loginRequest);
    ResponseDTO getAllUsers();
    ResponseDTO getUserBookingHistory(String userId);
    ResponseDTO getUserById(String userId);
    ResponseDTO deleteUser(String userId);
    ResponseDTO getMyInfo(String email);
}
