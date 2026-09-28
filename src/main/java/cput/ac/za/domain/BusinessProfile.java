package cput.ac.za.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

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
        this.verified = builder.verified;
    }

    // JSON key aliases below match the Business interface in the Angular
    // frontend (src/app/core/models/models.ts) without renaming any of the
    // underlying Java fields/getters used elsewhere in the backend.

    @JsonProperty("id")
    public Long getBusinessProfileID() {
        return businessProfileID;
    }

    public User getOwner() {
        return owner;
    }

    @JsonProperty("ownerId")
    public Long getOwnerId() {
        return owner != null ? owner.getUserID() : null;
    }

    // Full Category entity, for backend-internal use.
    public Category getCategory() {
        return category;
    }

    // Angular's Business.category is a plain string — expose the category
    // name under that same JSON key instead of the nested Category object.
    @JsonProperty("category")
    public String getCategoryName() {
        return category != null ? category.getName() : null;
    }

    @JsonProperty("name")
    public String getBusinessName() {
        return businessName;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public boolean isVerified() {
        return verified;
    }

    // ---- Placeholders -------------------------------------------------
    // The Angular Business model also expects "rating", "reviews" (count)
    // and "image", none of which exist as real backend data yet:
    // rating/review-count need an aggregate query over the Review table,
    // and there's no image-upload/storage layer (diagram's "File Storage"
    // box under External Services) built yet. Returning safe defaults here
    // so the frontend renders cleanly instead of showing "undefined" —
    // replace with real values once those features are built.
    @JsonProperty("rating")
    public double getRatingPlaceholder() {
        return 0;
    }

    @JsonProperty("reviews")
    public int getReviewCountPlaceholder() {
        return 0;
    }

    @JsonProperty("image")
    public String getImagePlaceholder() {
        return null;
    }

    @JsonProperty("area")
    public String getAreaPlaceholder() {
        return location;
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
                '}';
    }

    public static class Builder {
        private Long businessProfileID;
        private User owner;
        private Category category;
        private String businessName;
        private String description;
        private String location;
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
            this.verified = businessProfile.verified;
            return this;
        }

        public BusinessProfile build() {
            return new BusinessProfile(this);
        }
    }
}