package cput.ac.za.repository;

import cput.ac.za.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // A booking "belongs" to both a customer and a business owner — the
    // Angular BookingService.getMyBookings() call is used by both
    // dashboards, so this covers whichever side is asking.
    @Query("SELECT b FROM Booking b WHERE b.customer.userID = :userId OR b.businessProfile.owner.userID = :userId ORDER BY b.requestedAt DESC")
    List<Booking> findAllForUser(@Param("userId") Long userId);
}
