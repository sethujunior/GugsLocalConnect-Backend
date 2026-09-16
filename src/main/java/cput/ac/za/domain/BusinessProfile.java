package cput.ac.za.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "business_profile")
public class BusinessProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long businessProfileID;
    @OneToOne
    @JoinColumn(name = "ownerID")
    private User owner;
    @ManyToOne
    @JoinColumn(name = "categoryID")
    private Category category;
    private String businessName;
    private String description;
    private String location;
    private String contactInfo;
    private boolean verified;

    public BusinessProfile() {
    }

    public BusinessProfile(Builder builder) {
        this.businessProfileID = builder.businessProfileID;
        this.owner = builder.owner;
        this.category = builder.category;
        this.businessName = builder.businessName;
        this.description = builder.description;
        this.location = builder.location;
        this.contactInfo = builder.contactInfo;
        this.verified = builder.verified;
    }

    public Long getBusinessProfileID() {
        return businessProfileID;
    }

    public User getOwner() {
        return owner;
    }

    public Category getCategory() {
        return category;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public boolean isVerified() {
        return verified;
    }

    @Override
    public String toString() {
        return "BusinessProfile{" +
                "businessProfileID=" + businessProfileID +
                ", owner=" + owner +
                ", category=" + category +
                ", businessName='" + businessName + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", verified=" + verified +
                '}';
    }

    public static class Builder {
        private Long businessProfileID;
        private User owner;
        private Category category;
        private String businessName;
        private String description;
        private String location;
        private String contactInfo;
        private boolean verified;

        public Builder setBusinessProfileID(Long businessProfileID) {
            this.businessProfileID = businessProfileID;
            return this;
        }
        public Builder setOwner(User owner) {
            this.owner = owner;
            return this;
        }
        public Builder setCategory(Category category) {
            this.category = category;
            return this;
        }
        public Builder setBusinessName(String businessName) {
            this.businessName = businessName;
            return this;
        }
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }
        public Builder setLocation(String location) {
            this.location = location;
            return this;
        }
        public Builder setContactInfo(String contactInfo) {
            this.contactInfo = contactInfo;
            return this;
        }
        public Builder setVerified(boolean verified) {
            this.verified = verified;
            return this;
        }

        public Builder copy(BusinessProfile businessProfile) {
            this.businessProfileID = businessProfile.businessProfileID;
            this.owner = businessProfile.owner;
            this.category = businessProfile.category;
            this.businessName = businessProfile.businessName;
            this.description = businessProfile.description;
            this.location = businessProfile.location;
            this.contactInfo = businessProfile.contactInfo;
            this.verified = businessProfile.verified;
            return this;
        }

        public BusinessProfile build() {
            return new BusinessProfile(this);
        }
    }
}