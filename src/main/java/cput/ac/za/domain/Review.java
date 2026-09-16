package cput.ac.za.domain;

import jakarta.persistence.*;

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

    public Review() {
    }

    public Review(Builder builder) {
        this.reviewID = builder.reviewID;
        this.businessProfile = builder.businessProfile;
        this.customer = builder.customer;
        this.rating = builder.rating;
        this.comment = builder.comment;}


    public Long getReviewID() {
        return reviewID;
    }

    public BusinessProfile getBusinessProfile() {
        return businessProfile;
    }

    public User getCustomer() {
        return customer;
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
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

        public Builder copy(Review review) {
            this.reviewID = review.reviewID;
            this.businessProfile = review.businessProfile;
            this.customer = review.customer;
            this.rating = review.rating;
            this.comment = review.comment;
            return this;
        }

        public Review build() {
            return new Review(this);
        }
    }
}