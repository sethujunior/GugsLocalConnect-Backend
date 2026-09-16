package cput.ac.za.service;

import cput.ac.za.domain.Review;
import cput.ac.za.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService implements IService<Review,Long> {

    private ReviewRepository reviewRepository ;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review create(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review read(Long Id) {
        return reviewRepository.findById(Id).orElse(null);
    }

    @Override
    public Review update(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public boolean delete(Long Id) {
        if (reviewRepository.existsById(Id)) {
            reviewRepository.deleteById(Id);
            return true;
        }
        return false;
    }

    @Override
    public List<Review> getAll() {
        return reviewRepository.findAll();
    }
}
