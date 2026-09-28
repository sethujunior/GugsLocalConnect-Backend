package cput.ac.za.dto;

import java.time.LocalDate;

public class CreateBookingRequest {
    private Long businessId;
    private Long serviceId;
    private LocalDate date;

    public CreateBookingRequest() {
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
