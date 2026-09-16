package cput.ac.za.controller;

import cput.ac.za.domain.Review;
import cput.ac.za.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reviews")
public class ReviewController {

    private ReviewService reviewService;
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/create")
    public Review create(@RequestBody Review review) {
        return reviewService.create(review);
    }

    @GetMapping("/read/{reviewId}")
    public Review read(@PathVariable Long reviewId) {
    return reviewService.read(reviewId);
    }

    @PutMapping("/update")
    public Review update(@RequestBody Review review) {
        return reviewService.update(review);
    }

    @DeleteMapping("/delete/{userId}")
    public boolean delete(@PathVariable Long userId) {
        return reviewService.delete(userId);
    }

    @GetMapping("/getAll")
    public List<Review> getAll() {
        return reviewService.getAll();
    }

}