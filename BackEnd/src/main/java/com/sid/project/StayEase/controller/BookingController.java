package com.sid.project.StayEase.controller;

import com.sid.project.StayEase.dto.ResponseDTO;
import com.sid.project.StayEase.entity.Booking;
import com.sid.project.StayEase.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@CrossOrigin
@Tag(name = "Booking APIs", description = "book, allBookings, bookingsByConfirmationCode, cancelBooking")
public class BookingController
{
    @Autowired
    private BookingService bookingService;

    @Operation(description = "booking room on the basis of roomId and userId")
    @PostMapping("/bookRoom/{roomId}/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<ResponseDTO> saveBooking(@PathVariable("roomId") Long roomId,
                                                   @PathVariable("userId") Long userId,
                                                   @RequestBody Booking bookingRequest)
    {
        ResponseDTO responseDTO = bookingService.saveBooking(roomId, userId, bookingRequest);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @Operation(description = "getting booking details on confirmation code")
    @GetMapping("/bookingByConfirmationCode/{confirmationCode}")
    public ResponseEntity<ResponseDTO> getBookingByConfirmationCode(@PathVariable("confirmationCode") String confirmationCode)
    {
        ResponseDTO booking = bookingService.findBookingByConfirmationCode(confirmationCode);
        return ResponseEntity.status(booking.getStatusCode()).body(booking);
    }

    @Operation(description = "get all bookings")
    @GetMapping("/allBookings")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllBookings()
    {
        ResponseDTO allBookings = bookingService.getAllBookings();
        return ResponseEntity.status(allBookings.getStatusCode()).body(allBookings);
    }

    @Operation(description = "cancel booking with bookingId")
    @DeleteMapping("/cancelBooking/{bookingId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<ResponseDTO> cancelBooking(@PathVariable("bookingId") Long bookingId)
    {
        ResponseDTO responseDTO = bookingService.cancelBooking(bookingId);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }
}
