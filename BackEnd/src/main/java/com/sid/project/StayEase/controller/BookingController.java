package com.sid.project.StayEase.controller;

import com.sid.project.StayEase.dto.ResponseDTO;
import com.sid.project.StayEase.entity.Booking;
import com.sid.project.StayEase.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController
{
    @Autowired
    private BookingService bookingService;

    @PostMapping("/bookRoom/{roomId}/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<ResponseDTO> saveBooking(@PathVariable("roomId") Long roomId,
                                                   @PathVariable("userId") Long userId,
                                                   @RequestBody Booking bookingRequest)
    {
        ResponseDTO responseDTO = bookingService.saveBooking(roomId, userId, bookingRequest);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @GetMapping("/bookingByConfirmationCode/{confirmationCode}")
    public ResponseEntity<ResponseDTO> getBookingByConfirmationCode(@PathVariable("confirmationCode") String confirmationCode)
    {
        ResponseDTO booking = bookingService.findBookingByConfirmationCode(confirmationCode);
        return ResponseEntity.status(booking.getStatusCode()).body(booking);
    }

    @GetMapping("/allBookings")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllBookings()
    {
        ResponseDTO allBookings = bookingService.getAllBookings();
        return ResponseEntity.status(allBookings.getStatusCode()).body(allBookings);
    }

    @DeleteMapping("/cancelBooking/{bookingId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<ResponseDTO> cancelBooking(@PathVariable("bookingId") Long bookingId)
    {
        ResponseDTO responseDTO = bookingService.cancelBooking(bookingId);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }
}
