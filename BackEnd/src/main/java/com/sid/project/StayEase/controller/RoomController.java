package com.sid.project.StayEase.controller;

import com.sid.project.StayEase.dto.ResponseDTO;
import com.sid.project.StayEase.service.BookingService;
import com.sid.project.StayEase.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@CrossOrigin
@Tag(name = "Room APIs", description = "getRoom, updateRoom, getAllRooms, getAllRoomTypes, deleteRoom, updateRoom")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingService bookingService;

    @Operation(description = "add new room")
    @PostMapping("/addRoom")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> addNewRoom(
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam(value = "roomType", required = false) String roomType,
            @RequestParam(value = "roomPrice", required = false) BigDecimal roomPrice,
            @RequestParam(value = "roomDescription", required = false) String roomDescription
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

    @Operation(description = "all types of room")
    @GetMapping("/getAllRoomType")
    public List<String> getAllRoomType() {
        return roomService.getAllRoomType();
    }

    @Operation(description = "get all rooms")
    @GetMapping("/getAllRooms")
    public ResponseEntity<ResponseDTO> getAllRooms() {
        ResponseDTO allRooms = roomService.getAllRooms();
        return ResponseEntity.status(allRooms.getStatusCode()).body(allRooms);
    }

    @Operation(description = "delete room by roomId")
    @DeleteMapping("/deleteRoom/{roomId}")
    public ResponseEntity<ResponseDTO> deleteRoomById(@PathVariable("roomId") Long roomId) {
        ResponseDTO deletedRoom = roomService.deleteRoom(roomId);
        return ResponseEntity.status(deletedRoom.getStatusCode()).body(deletedRoom);
    }

    @Operation(description = "update the room by roomId")
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

    @Operation(description = "get room by roomId")
    @GetMapping("/getRoom/{roomId}")
    public ResponseEntity<ResponseDTO> getRoomById(@PathVariable("roomId") Long roomId) {
        ResponseDTO responseDTO = roomService.getRoomById(roomId);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @Operation(description = "available rooms by date")
    @GetMapping("/availableRoomsByDate")
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

    @Operation(description = "get all available rooms")
    @GetMapping("/getAllAvailableRooms")
    public ResponseEntity<ResponseDTO> getAllAvailableRooms() {
        ResponseDTO responseDTO = roomService.getAllAvailableRooms();
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }
}
