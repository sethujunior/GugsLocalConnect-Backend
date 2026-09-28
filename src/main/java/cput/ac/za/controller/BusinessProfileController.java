package cput.ac.za.controller;

import cput.ac.za.domain.BusinessProfile;
import cput.ac.za.security.CurrentUser;
import cput.ac.za.service.BusinesProfileService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/businesses")
public class BusinessProfileController {

    private final BusinesProfileService businesProfileService;

    public BusinessProfileController(BusinesProfileService businesProfileService) {
        this.businesProfileService = businesProfileService;
    }

    @GetMapping("/getAll")
    public List<BusinessProfile> getAll() {
        return businesProfileService.getAll();
    }

    @GetMapping("/read/{businessProfileId}")
    public BusinessProfile read(@PathVariable Long businessProfileId) {
        return businesProfileService.read(businessProfileId);
    }

    // The Angular BusinessService calls GET/PUT /api/businesses/me for the
    // logged-in business owner's own profile — resolved from the JWT,
    // not a path parameter, so an owner can't edit someone else's profile
    // by guessing an ID.
    @GetMapping("/me")
    public BusinessProfile getMine(Authentication authentication) {
        return businesProfileService.findByOwnerId(CurrentUser.id(authentication));
    }

    @PutMapping("/me")
    public BusinessProfile updateMine(Authentication authentication, @RequestBody BusinessProfile updates) {
        BusinessProfile existing = businesProfileService.findByOwnerId(CurrentUser.id(authentication));

        BusinessProfile updated = new BusinessProfile.Builder()
                .copy(existing)
                .setBusinessName(updates.getBusinessName() != null ? updates.getBusinessName() : existing.getBusinessName())
                .setDescription(updates.getDescription() != null ? updates.getDescription() : existing.getDescription())
                .setLocation(updates.getLocation() != null ? updates.getLocation() : existing.getLocation())
                .build();

        return businesProfileService.update(updated);
    }

    @DeleteMapping("/delete/{businessProfileId}")
    public boolean delete(@PathVariable Long businessProfileId) {
        return businesProfileService.delete(businessProfileId);
    }
}
