package cput.ac.za.repository;

import cput.ac.za.domain.BusinessProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessProfileRepository extends JpaRepository<BusinessProfile, Long> {
    Optional<BusinessProfile> findByOwner_UserID(Long ownerId);
}
