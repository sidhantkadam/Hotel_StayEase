package com.sid.project.StayEase.service;

import com.sid.project.StayEase.dto.ResponseDTO;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    ResponseDTO addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description);

    List<String> getAllRoomType();

    ResponseDTO getAllRooms();

    ResponseDTO deleteRoom(Long roomId);

    ResponseDTO updateRoom(Long roomId, MultipartFile photo, String roomType, BigDecimal roomPrice, String description);

    ResponseDTO getRoomById(Long roomId);

    ResponseDTO getAvailableRoomByDateAndTime(LocalDate checkInDate, LocalDate checkOutDate, String roomType);

    ResponseDTO getAllAvailableRooms();
}
