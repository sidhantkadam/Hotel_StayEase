package com.sid.project.StayEase.service;

import com.sid.project.StayEase.dto.BookingDTO;
import com.sid.project.StayEase.dto.ResponseDTO;
import com.sid.project.StayEase.dto.RoomDTO;
import com.sid.project.StayEase.entity.Booking;
import com.sid.project.StayEase.entity.Room;
import com.sid.project.StayEase.entity.User;
import com.sid.project.StayEase.exception.CommonException;
import com.sid.project.StayEase.repository.BookingRepository;
import com.sid.project.StayEase.repository.RoomRepository;
import com.sid.project.StayEase.repository.UserRepository;
import com.sid.project.StayEase.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseDTO saveBooking(Long roomId, Long userId, Booking bookingRequest) {

        ResponseDTO responseDTO = new ResponseDTO();

        try {
            if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
                throw new IllegalArgumentException("Please provide appropriate CheckIn and CheckOut dates");
            }
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new CommonException("Room not found "));
            User user = userRepository.findById(userId).orElseThrow(() -> new CommonException("User not found "));

            List<Booking> existingBooking = room.getBookings();

            if (!roomIsAvailable(bookingRequest, existingBooking)) {
                throw new CommonException("Room is not available for selected range of dates");
            }

            bookingRequest.setRoom(room);
            bookingRequest.setUser(user);
            String generateConfirmationCode = Utils.createRandomConfirmationCode(10);
            bookingRequest.setBookingConfirmationCode(generateConfirmationCode);
            bookingRepository.save(bookingRequest);

            responseDTO.setStatusCode(200);
            responseDTO.setMessage("successful");
            responseDTO.setBookingConfirmationCode(generateConfirmationCode);
        } catch (CommonException e) {
            responseDTO.setStatusCode(404);
            responseDTO.setMessage(e.getMessage());
        } catch (Exception e) {
            responseDTO.setStatusCode(500);
            responseDTO.setMessage("Error while saving the bookings" + e.getMessage());
        }
        return responseDTO;
    }


    @Override
    public ResponseDTO findBookingByConfirmationCode(String confirmationCode) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            Booking booking = bookingRepository.findByBookingConfirmationCode(confirmationCode)
                    .orElseThrow(() -> new CommonException("Booking not present for confirmation code: " + confirmationCode));
            BookingDTO bookingDTOS = Utils.mapBookingsEntityToBookingsDTO(booking);

            responseDTO.setStatusCode(200);
            responseDTO.setMessage("successful");
            responseDTO.setBooking(bookingDTOS);
        } catch (CommonException e) {
            responseDTO.setStatusCode(404);
            responseDTO.setMessage(e.getMessage());
        }
        catch (Exception e) {
            responseDTO.setStatusCode(500);
            responseDTO.setMessage("Error while finding bookings by confirmation code" + e.getMessage());
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO getAllBookings() {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<Booking> bookingList = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<BookingDTO> bookingDTOList = Utils.mapBookingListEntityToBookingListDTO(bookingList);

            responseDTO.setStatusCode(200);
            responseDTO.setMessage("successful");
            responseDTO.setBookingList(bookingDTOList);
        } catch (Exception e) {
            responseDTO.setStatusCode(500);
            responseDTO.setMessage("Error while getting the all bookings !" + e.getMessage());
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO cancelBooking(Long bookingId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            bookingRepository.findById(bookingId).orElseThrow(() -> new CommonException("Booking not found for booking id :" + bookingId));
            bookingRepository.deleteById(bookingId);
            responseDTO.setStatusCode(200);
            responseDTO.setMessage("successful");
        } catch (Exception e) {
            responseDTO.setStatusCode(500);
            responseDTO.setMessage("Error while canceling the booking !" + e.getMessage());
        }
        return responseDTO;
    }

    private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {

        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );
    }
}
