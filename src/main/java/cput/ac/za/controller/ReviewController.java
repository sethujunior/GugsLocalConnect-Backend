package cput.ac.za.controller;

import cput.ac.za.domain.BusinessProfile;
import cput.ac.za.domain.Review;
import cput.ac.za.domain.User;
import cput.ac.za.dto.CreateReviewRequest;
import cput.ac.za.security.CurrentUser;
import cput.ac.za.service.BusinesProfileService;
import cput.ac.za.service.ReviewService;
import cput.ac.za.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final BusinesProfileService businesProfileService;
    private final UserService userService;

    public ReviewController(ReviewService reviewService, BusinesProfileService businesProfileService, UserService userService) {
        this.reviewService = reviewService;
        this.businesProfileService = businesProfileService;
        this.userService = userService;
    }

    // Public — matches the Angular ReviewService.getForBusiness() call from
    // the business-detail page, which anyone can view without logging in.
    @GetMapping("/business/{businessId}")
    public List<Review> getForBusiness(@PathVariable Long businessId) {
        return reviewService.getForBusiness(businessId);
    }

    @PostMapping
    public Review submit(Authentication authentication, @RequestBody CreateReviewRequest request) {
        User customer = userService.read(CurrentUser.id(authentication));
        BusinessProfile business = businesProfileService.read(request.getBusinessId());

        if (business == null) {
            throw new RuntimeException("Business not found");
        }

        Review review = new Review.Builder()
                .setBusinessProfile(business)
                .setCustomer(customer)
                .setRating(request.getRating())
                .setComment(request.getComment())
                .build();

        return reviewService.create(review);
    }

    @GetMapping("/getAll")
    public List<Review> getAll() {
        return reviewService.getAll();
    }

    @DeleteMapping("/delete/{reviewId}")
    public boolean delete(@PathVariable Long reviewId) {
        return reviewService.delete(reviewId);
    }
}
