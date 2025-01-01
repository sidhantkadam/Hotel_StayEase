package com.sid.project.StayEase.service;

import com.sid.project.StayEase.dto.LoginRequest;
import com.sid.project.StayEase.dto.ResponseDTO;
import com.sid.project.StayEase.dto.UserDTO;
import com.sid.project.StayEase.entity.User;
import com.sid.project.StayEase.exception.CommonException;
import com.sid.project.StayEase.repository.UserRepository;
import com.sid.project.StayEase.utils.JWTUtils;
import com.sid.project.StayEase.utils.Utils;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public ResponseDTO register(User user) {
        ResponseDTO response = new ResponseDTO();

        try {
            if (user.getRole() == null || user.getRole().isBlank()) {
                user.setRole("USER");
            }
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new CommonException(user.getEmail() + "Exists already !");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            UserDTO userDTO = Utils.mapUserEntitytoUserDTO(savedUser);

            response.setStatusCode(200);
            response.setUser(userDTO);
        } catch (CommonException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred at Registration of User " + e.getMessage());
        }
        return response;
    }

    @Override
    public ResponseDTO login(LoginRequest loginRequest) {

        ResponseDTO response = new ResponseDTO();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new CommonException("User not Found !"));
            var token = jwtUtils.generateToken(user);

            response.setStatusCode(200);
            response.setExpirationTime("7 Days");
            response.setMessage("Successful..");
            response.setToken(token);
            response.setRole(user.getRole());
        } catch (CommonException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred at Login of User " + e.getMessage());
        }
        return response;
    }

    @Override
    public ResponseDTO getAllUsers() {
        ResponseDTO response = new ResponseDTO();
        try {
            List<User> listUsers = userRepository.findAll();
            List<UserDTO> userDTOS = Utils.mapUserListEntityToUserListDTO(listUsers);

            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setUserList(userDTOS);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting for all Users " + e.getMessage());
        }
        return response;
    }

    @Override
    public ResponseDTO getUserBookingHistory(String userId) {
        ResponseDTO response = new ResponseDTO();
        try {
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new CommonException("user not found !"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTOPlusUserBookingAndRoom(user);

            response.setStatusCode(200);
            response.setUser(userDTO);
            response.setMessage("Successful");
        } catch (CommonException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting for Users booking history !" + e.getMessage());
        }
        return response;
    }

    @Override
    public ResponseDTO deleteUser(String userId) {
        ResponseDTO response = new ResponseDTO();
        try {
            userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new CommonException("user not found !"));
            userRepository.deleteById(Long.valueOf(userId));
            response.setStatusCode(200);
            response.setMessage("successful");
        } catch (CommonException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting for deleting user !" + e.getMessage());
        }
        return response;
    }

    @Override
    public ResponseDTO getUserById(String userId) {
        ResponseDTO response = new ResponseDTO();
        try {
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new CommonException("user not found !"));
            UserDTO userDTO = Utils.mapUserEntitytoUserDTO(user);

            response.setStatusCode(200);
            response.setUser(userDTO);
            response.setMessage("Successful");
        } catch (CommonException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting for user details by Id " + e.getMessage());
        }
        return response;
    }

    @Override
    public ResponseDTO getMyInfo(String email) {
        ResponseDTO response = new ResponseDTO();
        try {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new CommonException("user not found !"));
            UserDTO userDTO = Utils.mapUserEntitytoUserDTO(user);

            response.setStatusCode(200);
            response.setUser(userDTO);
            response.setMessage("Successful");
        } catch (CommonException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting for user info by Email Id" + e.getMessage());
        }
        return response;
    }
}
