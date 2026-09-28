package cput.ac.za.controller;

import cput.ac.za.domain.BusinessProfile;
import cput.ac.za.domain.BusinessService;
import cput.ac.za.security.CurrentUser;
import cput.ac.za.service.BServiceService;
import cput.ac.za.service.BusinesProfileService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BusinessServiceController {

    private final BServiceService service;
    private final BusinesProfileService businesProfileService;

    public BusinessServiceController(BServiceService service, BusinesProfileService businesProfileService) {
        this.service = service;
        this.businesProfileService = businesProfileService;
    }

    // The Angular BusinessService always operates on "my" services, scoped
    // to the logged-in business owner's own profile via the JWT.
    @GetMapping("/api/businesses/me/services")
    public List<BusinessService> getMine(Authentication authentication) {
        BusinessProfile profile = businesProfileService.findByOwnerId(CurrentUser.id(authentication));
        return service.findByBusinessProfileId(profile.getBusinessProfileID());
    }

    @PostMapping("/api/businesses/me/services")
    public BusinessService addMine(Authentication authentication, @RequestBody BusinessService incoming) {
        BusinessProfile profile = businesProfileService.findByOwnerId(CurrentUser.id(authentication));

        BusinessService toSave = new BusinessService.Builder()
                .setBusinessProfile(profile)
                .setName(incoming.getName())
                .setDescription(incoming.getDescription())
                .setPrice(incoming.getPrice())
                .setPriceRange(incoming.getPriceRange())
                .build();

        return service.create(toSave);
    }

    @DeleteMapping("/api/businesses/me/services/{serviceId}")
    public boolean deleteMine(Authentication authentication, @PathVariable Long serviceId) {
        BusinessProfile profile = businesProfileService.findByOwnerId(CurrentUser.id(authentication));
        BusinessService existing = service.read(serviceId);

        if (existing == null || existing.getBusinessProfile() == null
                || !existing.getBusinessProfile().getBusinessProfileID().equals(profile.getBusinessProfileID())) {
            throw new RuntimeException("You can only delete your own services");
        }

        return service.delete(serviceId);
    }

    // Plain admin/read-only endpoints, unscoped to a particular business.
    @GetMapping("/api/services/getAll")
    public List<BusinessService> getAll() {
        return service.getAll();
    }

    @GetMapping("/api/services/read/{serviceId}")
    public BusinessService read(@PathVariable Long serviceId) {
        return service.read(serviceId);
    }

    // Public — a customer browsing a business needs to see its services
    // before booking, without needing to be that business's owner.
    // The {businessId:[0-9]+} constraint stops this from accidentally
    // matching "/api/businesses/me/services" (the owner-only endpoint above).
    @GetMapping("/api/businesses/{businessId:[0-9]+}/services")
    public List<BusinessService> getForBusiness(@PathVariable Long businessId) {
        return service.findByBusinessProfileId(businessId);
    }
}
