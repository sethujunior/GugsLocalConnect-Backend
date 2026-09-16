package cput.ac.za.service;

import cput.ac.za.domain.BusinessProfile;
import cput.ac.za.repository.BusinessProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinesProfileService implements IBusinessProfile{

    private BusinessProfileRepository businessProfileRepository;

    public BusinesProfileService(BusinessProfileRepository businessProfileRepository) {
        this.businessProfileRepository = businessProfileRepository;
    }

    @Override
    public BusinessProfile create(BusinessProfile businessProfile) {
        return businessProfileRepository.save(businessProfile);
    }

    @Override
    public BusinessProfile read(Long aLong) {
        return businessProfileRepository.findById(aLong).orElse(null);
    }

    @Override
    public BusinessProfile update(BusinessProfile businessProfile) {
        return businessProfileRepository.save(businessProfile);
    }

    @Override
    public boolean delete(Long Id) {
        if (businessProfileRepository.existsById(Id)) {
            businessProfileRepository.deleteById(Id);
            return true;
        }
        return false;
    }

    @Override
    public List<BusinessProfile> getAll() {
        return businessProfileRepository.findAll();
    }
}
