package com.sid.project.StayEase.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bookings")
public class Booking
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "check in time is required !")
    private LocalDateTime checkInTime;

    @Future(message = "check out time required in future")
    private LocalDateTime checkOutTime;

    @Min(value = 1, message = "minimum one adult is required !")
    private int noOfAdults;

    @Min(value = 0, message = "min 0 child is required, less than zero is not allowed !")
    private int noOfChild;

    private int totalGuests;

    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public void calculateTotalGuests()
    {
        this.totalGuests = this.noOfChild + this.noOfAdults;
    }

    public void setNoOfAdults(int noOfAdults) {
        this.noOfAdults = noOfAdults;
        calculateTotalGuests();
    }

    public void setNoOfChild(int noOfChild) {
        this.noOfChild = noOfChild;
        calculateTotalGuests();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "Id=" + id +
                ", checkInTime=" + checkInTime +
                ", checkOutTime=" + checkOutTime +
                ", noOfAdults=" + noOfAdults +
                ", noOfChild=" + noOfChild +
                ", totalGuests=" + totalGuests +
                ", bookingConfirmationCode='" + bookingConfirmationCode + '\'' +
                '}';
    }
}
