package cput.ac.za.dto;

import cput.ac.za.domain.BookingStatus;

public class UpdateBookingStatusRequest {
    private BookingStatus status;

    public UpdateBookingStatusRequest() {
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}
