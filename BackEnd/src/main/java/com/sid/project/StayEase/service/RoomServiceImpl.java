package com.sid.project.StayEase.service;

import com.sid.project.StayEase.dto.ResponseDTO;
import com.sid.project.StayEase.dto.RoomDTO;
import com.sid.project.StayEase.entity.Room;
import com.sid.project.StayEase.exception.CommonException;
import com.sid.project.StayEase.repository.BookingRepository;
import com.sid.project.StayEase.repository.RoomRepository;
import com.sid.project.StayEase.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private AwsS3Service awsS3Service;


    @Override
    public ResponseDTO addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            String imgUrl = awsS3Service.saveImageToS3(photo);

            Room room = new Room();
            room.setRoomPhotoUrl(imgUrl);
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setRoomDescription(description);

            Room saveRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(saveRoom);

            responseDTO.setStatusCode(200);
            responseDTO.setRoom(roomDTO);
            responseDTO.setMessage("successful");
        } catch (Exception e) {
            responseDTO.setStatusCode(500);
            responseDTO.setMessage("Error saving the new room !" + e.getMessage());
        }
        return responseDTO;
    }

    @Override
    public List<String> getAllRoomType() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public ResponseDTO getAllRooms() {

        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<Room> roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);

            responseDTO.setStatusCode(200);
            responseDTO.setMessage("successful");
            responseDTO.setRoomList(roomDTOList);
        } catch (Exception e) {
            responseDTO.setStatusCode(500);
            responseDTO.setMessage("Error getting the new room !" + e.getMessage());
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO deleteRoom(Long roomId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            roomRepository.findById(roomId).orElseThrow(() -> new CommonException("Room not found with id :" + roomId));
            roomRepository.deleteById(roomId);
            responseDTO.setStatusCode(200);
            responseDTO.setMessage("successfully deleted room of id: " + roomId);
        }
        catch (CommonException e)
        {
            responseDTO.setStatusCode(404);
            responseDTO.setMessage(e.getMessage());
        }
        catch (Exception e) {
            responseDTO.setStatusCode(500);
            responseDTO.setMessage("Error deleting the new room !" + e.getMessage());
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO updateRoom(Long roomId, MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            String imgUrl = null;
            imgUrl = awsS3Service.saveImageToS3(photo);
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new CommonException("Room not found with id :" + roomId));

            if(roomType!=null) room.setRoomType(roomType);
            if(roomPrice!=null) room.setRoomPrice(roomPrice);
            if(description!=null) room.setRoomDescription(description);
            if(imgUrl!=null) room.setRoomPhotoUrl(imgUrl);

            Room updatedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(updatedRoom);

            responseDTO.setRoom(roomDTO);
            responseDTO.setStatusCode(200);
            responseDTO.setMessage("successfully updated room of id: " + roomId);
        }
        catch (CommonException e)
        {
            responseDTO.setStatusCode(404);
            responseDTO.setMessage(e.getMessage());
        }
        catch (Exception e) {
            responseDTO.setStatusCode(500);
            responseDTO.setMessage("Error updating the new room !" + e.getMessage());
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO getRoomById(Long roomId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new CommonException("Room not found with id :" + roomId));
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTOPlusBookings(room);
            responseDTO.setStatusCode(200);
            responseDTO.setMessage("successful");
            responseDTO.setRoom(roomDTO);
        }
        catch (CommonException e)
        {
            responseDTO.setStatusCode(404);
            responseDTO.setMessage(e.getMessage());
        }
        catch (Exception e) {
            responseDTO.setStatusCode(500);
            responseDTO.setMessage("Error getting the room by id and booking !" + e.getMessage());
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO getAvailableRoomByDateAndTime(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<Room> availableRoom = roomRepository.getRoomOnCheckInDateAndRoomType(checkInDate, checkOutDate, roomType);

            List<RoomDTO> roomDTO  = Utils.mapRoomListEntityToRoomListDTO(availableRoom);
            responseDTO.setStatusCode(200);
            responseDTO.setMessage("successful");
            responseDTO.setRoomList(roomDTO);
        }
        catch (Exception e) {
            responseDTO.setStatusCode(500);
            responseDTO.setMessage("Error while getting the room list based on booking dates" + e.getMessage());
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO getAllAvailableRooms() {
        ResponseDTO responseDTO = new ResponseDTO();
        try {

            List<Room> roomList = roomRepository.getAllAvailableRooms();
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);
            responseDTO.setStatusCode(200);
            responseDTO.setMessage("successful");
            responseDTO.setRoomList(roomDTOList);
        }
        catch (Exception e) {
            responseDTO.setStatusCode(500);
            responseDTO.setMessage("Error while getting All available rooms !" + e.getMessage());
        }
        return responseDTO;
    }
}
