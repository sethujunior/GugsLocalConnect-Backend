package cput.ac.za.controller;

import cput.ac.za.service.BookingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("booking")
public class BookingServiceController {

    private BookingService bookingService;

    public BookingServiceController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

}
