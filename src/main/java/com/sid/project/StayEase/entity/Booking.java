package com.sid.project.StayEase.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "check in time is required !")
    private LocalDate checkInDate;

    @Future(message = "check out time required in future")
    private LocalDate checkOutDate;

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

    public void calculateTotalGuests() {
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
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", noOfAdults=" + noOfAdults +
                ", noOfChild=" + noOfChild +
                ", totalGuests=" + totalGuests +
                ", bookingConfirmationCode='" + bookingConfirmationCode + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "check in time is required !") LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(@NotNull(message = "check in time is required !") LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public @Future(message = "check out time required in future") LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(@Future(message = "check out time required in future") LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    @Min(value = 1, message = "minimum one adult is required !")
    public int getNoOfAdults() {
        return noOfAdults;
    }

    @Min(value = 0, message = "min 0 child is required, less than zero is not allowed !")
    public int getNoOfChild() {
        return noOfChild;
    }

    public int getTotalGuests() {
        return totalGuests;
    }

    public void setTotalGuests(int totalGuests) {
        this.totalGuests = totalGuests;
    }

    public String getBookingConfirmationCode() {
        return bookingConfirmationCode;
    }

    public void setBookingConfirmationCode(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
