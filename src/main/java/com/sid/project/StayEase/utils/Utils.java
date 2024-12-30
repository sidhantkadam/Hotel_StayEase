package com.sid.project.StayEase.utils;

import com.sid.project.StayEase.dto.BookingDTO;
import com.sid.project.StayEase.dto.RoomDTO;
import com.sid.project.StayEase.dto.UserDTO;
import com.sid.project.StayEase.entity.Booking;
import com.sid.project.StayEase.entity.Room;
import com.sid.project.StayEase.entity.User;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class Utils
{
    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVXYZ0123456789";

    private static final SecureRandom secureRandom = new SecureRandom();

    public static String createRandomConfirmationCode(int length)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<length;i++)
        {
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char c = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static UserDTO mapUserEntitytoUserDTO(User user)
    {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public static UserDTO mapUserEntityToUserDTOPlusUserBookingAndRoom(User user)
    {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());

        if(!user.getBookings().isEmpty())
        {
            userDTO.setBookings(user.getBookings().stream()
                    .map(booking -> mapBookingEntityToDTOPlusBookedRoom(booking, false))
                    .collect(Collectors.toList()));
        }
        return userDTO;
    }

    public static RoomDTO mapRoomEntityToRoomDTO(Room room)
    {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setRoomType(room.getRoomType());
        roomDTO.setRoomPrice(room.getRoomPrice());
        roomDTO.setRoomPhotoUrl(room.getRoomPhotoUrl());
        roomDTO.setRoomDescription(room.getRoomDescription());
        return roomDTO;
    }

    public static RoomDTO mapRoomEntityToRoomDTOPlusBookings(Room room)
    {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setRoomType(room.getRoomType());
        roomDTO.setRoomPrice(room.getRoomPrice());
        roomDTO.setRoomPhotoUrl(room.getRoomPhotoUrl());
        roomDTO.setRoomDescription(room.getRoomDescription());

        if(room.getBookings() != null)
        {
            roomDTO.setBookings(room.getBookings().stream().map(Utils :: mapBookingsEntityToBookingsDTO).collect(Collectors.toList()));
        }
        return roomDTO;
    }

    public static BookingDTO mapBookingsEntityToBookingsDTO(Booking booking)
    {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setCheckOutDate(booking.getCheckOutDate());
        bookingDTO.setNoOfChild(booking.getNoOfChild());
        bookingDTO.setNoOfAdults(booking.getNoOfAdults());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());
        bookingDTO.setTotalGuests(booking.getTotalGuests());
        return bookingDTO;
    }

    public static BookingDTO mapBookingEntityToDTOPlusBookedRoom(Booking booking, boolean mapUser)
    {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setCheckOutDate(booking.getCheckOutDate());
        bookingDTO.setNoOfChild(booking.getNoOfChild());
        bookingDTO.setNoOfAdults(booking.getNoOfAdults());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());
        bookingDTO.setTotalGuests(booking.getTotalGuests());

        if(mapUser)
        {
            bookingDTO.setUserDTO(Utils.mapUserEntitytoUserDTO(booking.getUser()));
        }
        if(booking.getRoom() !=null)
        {
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setId(booking.getRoom().getId());
            roomDTO.setRoomType(booking.getRoom().getRoomType());
            roomDTO.setRoomPrice(booking.getRoom().getRoomPrice());
            roomDTO.setRoomDescription(booking.getRoom().getRoomDescription());
            roomDTO.setRoomPhotoUrl(booking.getRoom().getRoomPhotoUrl());
            bookingDTO.setRoomDTO(roomDTO);
        }
        return bookingDTO;
    }

    public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList)
    {
        return userList.stream().map(Utils::mapUserEntitytoUserDTO).collect(Collectors.toList());
    }

    public static List<BookingDTO> mapBookingListEntityToBookingListDTO(List<Booking> bookingList)
    {
        return bookingList.stream().map(Utils::mapBookingsEntityToBookingsDTO).collect(Collectors.toList());
    }

    public static List<RoomDTO> mapRoomListEntityToRoomListDTO(List<Room> roomList)
    {
        return roomList.stream().map(Utils::mapRoomEntityToRoomDTO).collect(Collectors.toList());
    }
}
