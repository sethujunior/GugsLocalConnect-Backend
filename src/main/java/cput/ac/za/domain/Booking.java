package cput.ac.za.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;
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
    // When the request was made (audit timestamp, set automatically).
    private LocalDateTime requestedAt;
    // The date the customer actually wants the service performed — this
    // didn't exist before; the Angular booking form collects it but there
    // was nowhere on this entity to put it.
    private LocalDate requestedDate;

    public Booking() {
    }

    public Booking(Builder builder) {
        this.bookingID = builder.bookingID;
        this.customer = builder.customer;
        this.businessProfile = builder.businessProfile;
        this.service = builder.service;
        this.status = builder.status;
        this.requestedAt = builder.requestedAt;
        this.requestedDate = builder.requestedDate;
    }

    @PrePersist
    protected void onCreate() {
        this.requestedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = BookingStatus.PENDING;
        }
    }

    @JsonProperty("id")
    public Long getBookingID() {
        return bookingID;
    }

    public User getCustomer() {
        return customer;
    }

    @JsonProperty("customerId")
    public Long getCustomerId() {
        return customer != null ? customer.getUserID() : null;
    }

    public BusinessProfile getBusinessProfile() {
        return businessProfile;
    }

    @JsonProperty("businessId")
    public Long getBusinessId() {
        return businessProfile != null ? businessProfile.getBusinessProfileID() : null;
    }

    public BusinessService getService() {
        return service;
    }

    @JsonProperty("serviceId")
    public Long getServiceId() {
        return service != null ? service.getServiceID() : null;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    // Angular's Booking.date maps to this — the customer-chosen date.
    @JsonProperty("date")
    public LocalDate getRequestedDate() {
        return requestedDate;
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
                ", requestedDate=" + requestedDate +
                '}';
    }

    public static class Builder {
        private Long bookingID;
        private User customer;
        private BusinessProfile businessProfile;
        private BusinessService service;
        private BookingStatus status;
        private LocalDateTime requestedAt;
        private LocalDate requestedDate;

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
        public Builder setRequestedDate(LocalDate requestedDate) {
            this.requestedDate = requestedDate;
            return this;
        }

        public Builder copy(Booking booking) {
            this.bookingID = booking.bookingID;
            this.customer = booking.customer;
            this.businessProfile = booking.businessProfile;
            this.service = booking.service;
            this.status = booking.status;
            this.requestedAt = booking.requestedAt;
            this.requestedDate = booking.requestedDate;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }
    }
}
