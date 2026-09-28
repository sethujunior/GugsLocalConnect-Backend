package cput.ac.za.service;

import cput.ac.za.domain.BusinessProfile;
import cput.ac.za.domain.Category;
import cput.ac.za.domain.Role;
import cput.ac.za.domain.User;
import cput.ac.za.dto.BusinessSignupRequest;
import cput.ac.za.repository.BusinessProfileRepository;
import cput.ac.za.repository.CategoryRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BusinesProfileService implements IBusinessProfile {

    private final BusinessProfileRepository businessProfileRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public BusinesProfileService(
            BusinessProfileRepository businessProfileRepository,
            CategoryRepository categoryRepository,
            UserService userService,
            PasswordEncoder passwordEncoder
    ) {
        this.businessProfileRepository = businessProfileRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Creates the User (login identity, role = BUSINESS_OWNER) and the
    // linked BusinessProfile (business details) together, in one call —
    // goes through UserService so email-uniqueness and password hashing
    // stay in one place rather than being duplicated here.
    @Transactional
    public BusinessProfile signupBusiness(BusinessSignupRequest request) {

        if (userService.findByEmailRaw(request.getEmail()) != null) {
            throw new RuntimeException("Email already registered");
        }

        Category category = categoryRepository.findByName(request.getCategory())
                .orElseThrow(() -> new RuntimeException("Unknown category: " + request.getCategory()));

        User newOwner = new User.Builder()
                .setName(request.getName())
                .setEmail(request.getEmail())
                .setPasswordHash(passwordEncoder.encode(request.getPassword()))
                .setPhone(request.getPhone())
                .setRole(Role.BUSINESS_OWNER)
                .build();

        User savedOwner = userService.create(newOwner);

        BusinessProfile profile = new BusinessProfile.Builder()
                .setOwner(savedOwner)
                .setCategory(category)
                .setBusinessName(request.getBusinessName())
                .setLocation(request.getLocation())
                .setDescription(request.getDescription())
                .setVerified(false)
                .build();

        return businessProfileRepository.save(profile);
    }

    @Override
    public BusinessProfile create(BusinessProfile businessProfile) {
        return businessProfileRepository.save(businessProfile);
    }

    @Override
    public BusinessProfile read(Long id) {
        return businessProfileRepository.findById(id).orElse(null);
    }

    @Override
    public BusinessProfile update(BusinessProfile businessProfile) {
        return businessProfileRepository.save(businessProfile);
    }

    @Override
    public boolean delete(Long id) {
        if (businessProfileRepository.existsById(id)) {
            businessProfileRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<BusinessProfile> getAll() {
        return businessProfileRepository.findAll();
    }

    public BusinessProfile findByOwnerId(Long ownerId) {
        return businessProfileRepository.findByOwner_UserID(ownerId)
                .orElseThrow(() -> new RuntimeException("No business profile found for this account"));
    }

    public List<BusinessProfile> search(String q, String category) {
        String needle = q != null ? q.toLowerCase() : null;

        return businessProfileRepository.findAll().stream()
                .filter(bp -> category == null || category.isBlank()
                        || (bp.getCategory() != null && bp.getCategory().getName().equalsIgnoreCase(category)))
                .filter(bp -> needle == null || needle.isBlank()
                        || (bp.getBusinessName() != null && bp.getBusinessName().toLowerCase().contains(needle))
                        || (bp.getDescription() != null && bp.getDescription().toLowerCase().contains(needle))
                        || (bp.getCategory() != null && bp.getCategory().getName().toLowerCase().contains(needle)))
                .toList();
    }
}
