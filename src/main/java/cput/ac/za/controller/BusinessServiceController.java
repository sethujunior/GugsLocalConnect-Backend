package cput.ac.za.controller;

import cput.ac.za.domain.BusinessService;
import cput.ac.za.service.BServiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("BService")
public class BusinessServiceController {

    private BServiceService service;

    public BusinessServiceController(BServiceService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public BusinessService create(@RequestBody BusinessService businessService) {
        return service.create(businessService);
    }

    @GetMapping("/read/{serviceId}")
    public BusinessService read(@PathVariable Long serviceId) {
        return service.read(serviceId);
    }

    @PutMapping("/update")
    public BusinessService update(@RequestBody BusinessService businessService) {
        return service.update(businessService);
    }

    @DeleteMapping("/delete/{serviceId}")
    public boolean delete(@PathVariable Long serviceId) {
        return service.delete(serviceId);
    }

    @GetMapping("/getAll")
    public List<BusinessService> getAll() {
        return service.getAll();
    }

}
