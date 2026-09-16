package cput.ac.za.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingID;
    @ManyToOne
    @JoinColumn(name = "customerID")
    private User customer;
    @ManyToOne
    @JoinColumn(name = "businessProfileID")
    private BusinessProfile businessProfile;
    @ManyToOne
    @JoinColumn(name = "serviceID")
    private BusinessService service;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    private LocalDateTime requestedAt;

    public Booking() {
    }

    public Booking(Builder builder) {
        this.bookingID = builder.bookingID;
        this.customer = builder.customer;
        this.businessProfile = builder.businessProfile;
        this.service = builder.service;
        this.status = builder.status;
        this.requestedAt = builder.requestedAt;
    }

    @PrePersist
    protected void onCreate() {
        this.requestedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = BookingStatus.PENDING;
        }
    }

    public Long getBookingID() {
        return bookingID;
    }

    public User getCustomer() {
        return customer;
    }

    public BusinessProfile getBusinessProfile() {
        return businessProfile;
    }

    public BusinessService getService() {
        return service;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingID=" + bookingID +
                ", customer=" + customer +
                ", businessProfile=" + businessProfile +
                ", service=" + service +
                ", status=" + status +
                ", requestedAt=" + requestedAt +
                '}';
    }

    public static class Builder {
        private Long bookingID;
        private User customer;
        private BusinessProfile businessProfile;
        private BusinessService service;
        private BookingStatus status;
        private LocalDateTime requestedAt;

        public Builder setBookingID(Long bookingID) {
            this.bookingID = bookingID;
            return this;
        }
        public Builder setCustomer(User customer) {
            this.customer = customer;
            return this;
        }
        public Builder setBusinessProfile(BusinessProfile businessProfile) {
            this.businessProfile = businessProfile;
            return this;
        }
        public Builder setService(BusinessService service) {
            this.service = service;
            return this;
        }
        public Builder setStatus(BookingStatus status) {
            this.status = status;
            return this;
        }
        public Builder setRequestedAt(LocalDateTime requestedAt) {
            this.requestedAt = requestedAt;
            return this;
        }

        public Builder copy(Booking booking) {
            this.bookingID = booking.bookingID;
            this.customer = booking.customer;
            this.businessProfile = booking.businessProfile;
            this.service = booking.service;
            this.status = booking.status;
            this.requestedAt = booking.requestedAt;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }
    }
}