package cput.ac.za.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewID;
    @ManyToOne
    @JoinColumn(name = "businessProfileID")
    private BusinessProfile businessProfile;
    @ManyToOne
    @JoinColumn(name = "customerID")
    private User customer;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

    public Review() {
    }

    public Review(Builder builder) {
        this.reviewID = builder.reviewID;
        this.businessProfile = builder.businessProfile;
        this.customer = builder.customer;
        this.rating = builder.rating;
        this.comment = builder.comment;
        this.createdAt = builder.createdAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @JsonProperty("id")
    public Long getReviewID() {
        return reviewID;
    }

    public BusinessProfile getBusinessProfile() {
        return businessProfile;
    }

    @JsonProperty("businessId")
    public Long getBusinessId() {
        return businessProfile != null ? businessProfile.getBusinessProfileID() : null;
    }

    public User getCustomer() {
        return customer;
    }

    @JsonProperty("authorName")
    public String getAuthorName() {
        return customer != null ? customer.getName() : "Anonymous";
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewID=" + reviewID +
                ", businessProfile=" + businessProfile +
                ", customer=" + customer +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }

    public static class Builder {
        private Long reviewID;
        private BusinessProfile businessProfile;
        private User customer;
        private Integer rating;
        private String comment;
        private LocalDateTime createdAt;

        public Builder setReviewID(Long reviewID) {
            this.reviewID = reviewID;
            return this;
        }
        public Builder setBusinessProfile(BusinessProfile businessProfile) {
            this.businessProfile = businessProfile;
            return this;
        }
        public Builder setCustomer(User customer) {
            this.customer = customer;
            return this;
        }
        public Builder setRating(Integer rating) {
            this.rating = rating;
            return this;
        }
        public Builder setComment(String comment) {
            this.comment = comment;
            return this;
        }
        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder copy(Review review) {
            this.reviewID = review.reviewID;
            this.businessProfile = review.businessProfile;
            this.customer = review.customer;
            this.rating = review.rating;
            this.comment = review.comment;
            this.createdAt = review.createdAt;
            return this;
        }

        public Review build() {
            return new Review(this);
        }
    }
}
