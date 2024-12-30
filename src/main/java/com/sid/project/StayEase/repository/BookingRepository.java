package com.sid.project.StayEase.repository;

import com.sid.project.StayEase.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>
{
    Optional<Booking> findByBookingConfirmationCode(String confirmationCode);
}
