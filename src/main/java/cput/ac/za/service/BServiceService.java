package cput.ac.za.service;

import cput.ac.za.domain.BusinessService;
import cput.ac.za.repository.BusinessServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BServiceService implements IBService{

    public BusinessServiceRepository businessServiceRepository;

    public BServiceService(BusinessServiceRepository businessServiceRepository) {
        this.businessServiceRepository = businessServiceRepository;
    }

    @Override
    public BusinessService create(BusinessService businessService) {
        return businessServiceRepository.save(businessService);
    }

    @Override
    public BusinessService read(Long Id) {
        return businessServiceRepository.findById(Id).orElse(null);
    }

    @Override
    public BusinessService update(BusinessService businessService) {
        return businessServiceRepository.save(businessService);
    }

    @Override
    public boolean delete(Long Id) {
        if (businessServiceRepository.existsById(Id)) {
            businessServiceRepository.deleteById(Id);
            return true;
        }
        return false;
    }

    @Override
    public List<BusinessService> getAll() {
        return businessServiceRepository.findAll();
    }
}
