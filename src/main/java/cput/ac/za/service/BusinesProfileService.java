package cput.ac.za.service;

import cput.ac.za.domain.BusinessProfile;
import cput.ac.za.domain.Category;
import cput.ac.za.domain.Role;
import cput.ac.za.domain.User;
import cput.ac.za.dto.BusinessSignupRequest;
import cput.ac.za.repository.BusinessProfileRepository;
import cput.ac.za.repository.CategoryRepository;
import cput.ac.za.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinesProfileService implements IBusinessProfile {

    private BusinessProfileRepository businessProfileRepository;
    private CategoryRepository categoryRepository;
    private UserService userService;

    public BusinesProfileService(BusinessProfileRepository businessProfileRepository,
                                 CategoryRepository categoryRepository,
                                 UserService userService) {
        this.businessProfileRepository = businessProfileRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    // Creates the User (login identity, role = BUSINESS_OWNER) and the
    // linked BusinessProfile (business details) together, in one call.
    public BusinessProfile signupBusiness(BusinessSignupRequest request) {

        User newOwner = new User.Builder()
                .setName(request.getName())
                .setEmail(request.getEmail())
                .setPasswordHash(request.getPassword())
                .setPhone(request.getPhone())
                .setRole(Role.BUSINESS_OWNER)
                .build();

        User savedOwner = userService.create(newOwner);

        Category category = categoryRepository.findByName(request.getCategory());
        if (category == null) {
            throw new RuntimeException("Unknown category: " + request.getCategory());
        }

        BusinessProfile profile = new BusinessProfile.Builder()
                .setOwner(savedOwner)
                .setCategory(category)
                .setBusinessName(request.getBusinessName())
                .setLocation(request.getLocation())
                .setDescription(request.getDescription())
                .setVerified(false)
                .build();

        return businessProfileRepository.save(profile);
    private UserRepository userRepository;

    public BusinesProfileService(BusinessProfileRepository businessProfileRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.businessProfileRepository = businessProfileRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
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

    public BusinessProfile createBusiness(BusinessSignupRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User owner = new User.Builder()
                .setName(request.getName())
                .setEmail(request.getEmail())
                .setPasswordHash(request.getPassword())
                .setPhone(request.getPhone())
                .setRole(Role.BUSINESS_OWNER)
                .build();

        User savedOwner = userRepository.save(owner);

        Category category = categoryRepository.findByName(request.getCategory())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        BusinessProfile businessProfile = new BusinessProfile.Builder()
                .setOwner(savedOwner)
                .setCategory(category)
                .setBusinessName(request.getBusinessName())
                .setLocation(request.getLocation())
                .setDescription(request.getDescription())
                .build();

        return businessProfileRepository.save(businessProfile);
    }
}
