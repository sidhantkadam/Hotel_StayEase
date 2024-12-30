package com.sid.project.StayEase.controller;

import com.sid.project.StayEase.dto.ResponseDTO;
import com.sid.project.StayEase.service.BookingService;
import com.sid.project.StayEase.service.RoomService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingService bookingService;

    @PostMapping("/addRoom")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> addNewRoom(
            @RequestParam(value = "photo") MultipartFile photo,
            @RequestParam(value = "roomType") String roomType,
            @RequestParam(value = "roomPrice") BigDecimal roomPrice,
            @RequestParam(value = "roomDescription") String roomDescription
    ) {
        if (photo.isEmpty() || photo == null || roomPrice == null || roomType.isBlank() || roomType == null) {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setMessage("Please provide all required fields..");
            responseDTO.setStatusCode(400);
            return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
        }

        ResponseDTO responseDTO = roomService.addNewRoom(photo, roomType, roomPrice, roomDescription);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @GetMapping("/getAllRoomType")
    public List<String> getAllRoomType() {
        return roomService.getAllRoomType();
    }

    @GetMapping("/getAllRooms")
    public ResponseEntity<ResponseDTO> getAllRooms() {
        ResponseDTO allRooms = roomService.getAllRooms();
        return ResponseEntity.status(allRooms.getStatusCode()).body(allRooms);
    }

    @DeleteMapping("/deleteRoom/{roomId}")
    public ResponseEntity<ResponseDTO> deleteRoomById(@PathVariable("roomId") Long roomId) {
        ResponseDTO deletedRoom = roomService.deleteRoom(roomId);
        return ResponseEntity.status(deletedRoom.getStatusCode()).body(deletedRoom);
    }

    @PutMapping("/updateRoom/{roomId}")
    public ResponseEntity<ResponseDTO> updateById(
            @RequestParam(value = "roomId") Long roomId,
            @RequestParam(value = "photo") MultipartFile photo,
            @RequestParam(value = "roomType") String roomType,
            @RequestParam(value = "roomPrice") BigDecimal roomPrice,
            @RequestParam(value = "roomDescription") String roomDescription) {
        ResponseDTO responseDTO = roomService.updateRoom(roomId, photo, roomType, roomPrice, roomDescription);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @GetMapping("/getRoom/{roomId}")
    public ResponseEntity<ResponseDTO> getRoomById(@PathVariable("roomId") Long roomId) {
        ResponseDTO responseDTO = roomService.getRoomById(roomId);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @GetMapping("/available-rooms-by-date-and-type")
    public ResponseEntity<ResponseDTO> getAvailableRoomsByDateAndType(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam(required = false) String roomType
    ) {
        if (checkInDate == null || roomType == null || roomType.isBlank() || checkOutDate == null) {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setStatusCode(400);
            responseDTO.setMessage("Please provide values for all fields(checkInDate, roomType,checkOutDate)");
            return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
        }
        ResponseDTO responseDTO = roomService.getAvailableRoomByDateAndTime(checkInDate, checkOutDate, roomType);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @GetMapping("/getAllAvailableRooms")
    public ResponseEntity<ResponseDTO> getAllAvailableRooms() {
        ResponseDTO responseDTO = roomService.getAllAvailableRooms();
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }
}
