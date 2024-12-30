package com.sid.project.StayEase.service;

import com.sid.project.StayEase.dto.ResponseDTO;
import com.sid.project.StayEase.entity.Booking;

public interface BookingService {
    ResponseDTO saveBooking(Long roomId, Long userId, Booking bookingRequest);

    ResponseDTO findBookingByConfirmationCode(String confirmationCode);

    ResponseDTO getAllBookings();

    ResponseDTO cancelBooking(Long bookingId);
}
