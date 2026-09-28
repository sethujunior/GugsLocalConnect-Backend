package cput.ac.za.controller;

import cput.ac.za.domain.BusinessProfile;
import cput.ac.za.service.BusinesProfileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// New — matches "Search & Discovery" in the architecture diagram and the
// Angular SearchService, which had nothing to call before this existed.
@RestController
public class SearchController {

    private final BusinesProfileService businesProfileService;

    public SearchController(BusinesProfileService businesProfileService) {
        this.businesProfileService = businesProfileService;
    }

    @GetMapping("/api/search")
    public List<BusinessProfile> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String area // accepted for frontend compatibility; not yet a distinct field from "location"
    ) {
        return businesProfileService.search(q, category);
    }
}
