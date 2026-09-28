package cput.ac.za.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "service")
public class BusinessService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceID;
    @ManyToOne
    @JoinColumn(name = "businessProfileID")
    private BusinessProfile businessProfile;
    private String name;
    private String description;
    private String priceRange;
    // Added alongside priceRange (kept for backward compatibility) because
    // the Angular ServiceItem model sends/expects a plain number, not a
    // range string.
    private Double price;

    public BusinessService() {
    }

    public BusinessService(Builder builder) {
        this.serviceID = builder.serviceID;
        this.businessProfile = builder.businessProfile;
        this.name = builder.name;
        this.description = builder.description;
        this.priceRange = builder.priceRange;
        this.price = builder.price;
    }

    @JsonProperty("id")
    public Long getServiceID() {
        return serviceID;
    }

    @JsonProperty("businessId")
    public Long getBusinessId() {
        return businessProfile != null ? businessProfile.getBusinessProfileID() : null;
    }

    public BusinessProfile getBusinessProfile() {
        return businessProfile;
    }

    @JsonProperty("title")
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceID=" + serviceID +
                ", businessProfile=" + businessProfile +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", priceRange='" + priceRange + '\'' +
                ", price=" + price +
                '}';
    }

    public static class Builder {
        private Long serviceID;
        private BusinessProfile businessProfile;
        private String name;
        private String description;
        private String priceRange;
        private Double price;

        public Builder setServiceID(Long serviceID) {
            this.serviceID = serviceID;
            return this;
        }
        public Builder setBusinessProfile(BusinessProfile businessProfile) {
            this.businessProfile = businessProfile;
            return this;
        }
        public Builder setName(String name) {
            this.name = name;
            return this;
        }
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }
        public Builder setPriceRange(String priceRange) {
            this.priceRange = priceRange;
            return this;
        }
        public Builder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public Builder copy(BusinessService service) {
            this.serviceID = service.serviceID;
            this.businessProfile = service.businessProfile;
            this.name = service.name;
            this.description = service.description;
            this.priceRange = service.priceRange;
            this.price = service.price;
            return this;
        }

        public BusinessService build() {
            return new BusinessService(this);
        }
    }
}
