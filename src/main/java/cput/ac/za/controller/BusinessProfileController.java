package cput.ac.za.controller;

import cput.ac.za.domain.BusinessProfile;
import cput.ac.za.dto.BusinessSignupRequest;
import cput.ac.za.service.BusinesProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("businessprofile")
public class BusinessProfileController {

    public BusinesProfileService businesProfileService;

    public BusinessProfileController(BusinesProfileService businesProfileService) {
        this.businesProfileService = businesProfileService;
    }

    // Business signup: creates the User (login identity) and the
    // BusinessProfile (business details) together in one request.
    @PostMapping("/create-business")
    public BusinessProfile createBusiness(@RequestBody BusinessSignupRequest request) {
        return businesProfileService.signupBusiness(request);
    }

    @PostMapping("/create")
    public BusinessProfile create(@RequestBody BusinessProfile businessProfile) {
        return businesProfileService.create(businessProfile);
    }

    @GetMapping("/read/{businessProfileId}")
    public BusinessProfile read(@PathVariable Long businessProfileId) {
        return businesProfileService.read(businessProfileId);
    }

    @PutMapping("/update")
    public BusinessProfile update(@RequestBody BusinessProfile businessProfile) {
        return businesProfileService.update(businessProfile);
    }

    @DeleteMapping("/delete/{businessProfileId}")
    public boolean delete(@PathVariable Long businessProfileId) {
        return businesProfileService.delete(businessProfileId);
    }

    @GetMapping("/getAll")
    public List<BusinessProfile> getAll() {
        return businesProfileService.getAll();
    }
}

    @PostMapping("/create-business")
    public BusinessProfile createBusiness(@RequestBody BusinessSignupRequest request) {
        return businesProfileService.createBusiness(request);
    }
}
