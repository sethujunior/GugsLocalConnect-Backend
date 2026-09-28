package cput.ac.za.repository;

import cput.ac.za.domain.BusinessService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessServiceRepository extends JpaRepository<BusinessService, Long> {
    List<BusinessService> findByBusinessProfile_BusinessProfileID(Long businessProfileId);
}
