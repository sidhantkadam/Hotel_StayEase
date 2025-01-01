package com.sid.project.StayEase.controller;

import com.sid.project.StayEase.dto.ResponseDTO;
import com.sid.project.StayEase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UserService userService;

    @GetMapping("/allUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllUsers()
    {
        ResponseDTO allUsers = userService.getAllUsers();
        return ResponseEntity.status(allUsers.getStatusCode()).body(allUsers);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable("userId") String userId)
    {
        ResponseDTO user = userService.getUserById(userId);
        return ResponseEntity.status(user.getStatusCode()).body(user);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<ResponseDTO> deleteUserById(@PathVariable("userId") String userId)
    {
        ResponseDTO deletedUser = userService.deleteUser(userId);
        return ResponseEntity.status(deletedUser.getStatusCode()).body(deletedUser);
    }

    @GetMapping("/getUserBookings/{userId}")
    public ResponseEntity<ResponseDTO> getUserBookingHistory(@PathVariable("userId") String userId)
    {
        ResponseDTO userBookingHistory = userService.getUserBookingHistory(userId);
        return ResponseEntity.status(userBookingHistory.getStatusCode()).body(userBookingHistory);
    }

    @GetMapping("/getLoggedInUserInfo")
    public ResponseEntity<ResponseDTO> getLoggedInUserInfo()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        ResponseDTO myInfo = userService.getMyInfo(email);
        return ResponseEntity.status(myInfo.getStatusCode()).body(myInfo);
    }
}
