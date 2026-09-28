package cput.ac.za.controller;

import cput.ac.za.domain.*;
import cput.ac.za.dto.CreateBookingRequest;
import cput.ac.za.dto.UpdateBookingStatusRequest;
import cput.ac.za.security.CurrentUser;
import cput.ac.za.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import cput.ac.za.domain.Message;
import cput.ac.za.service.MessageService;
import java.util.List;

// Built from scratch — this controller was previously an empty class with
// no endpoints, even though Booking/BookingRepository/BookingService all
// already existed. Matches the "Booking Management" API module (booking
// requests, status, management) in the architecture diagram.
@RestController
@RequestMapping("/api/bookings")
public class BookingServiceController {

    private final BookingService bookingService;
    private final UserService userService;
    private final BusinesProfileService businesProfileService;
    private final BServiceService serviceService;
    private final MessageService messageService;

    public BookingServiceController(
            BookingService bookingService,
            UserService userService,
            BusinesProfileService businesProfileService,
            BServiceService serviceService,
            MessageService messageService
    ) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.businesProfileService = businesProfileService;
        this.serviceService = serviceService;
        this.messageService = messageService;
    }

    @PostMapping
    public Booking create(Authentication authentication, @RequestBody CreateBookingRequest request) {
        User customer = userService.read(CurrentUser.id(authentication));
        BusinessProfile business = businesProfileService.read(request.getBusinessId());
        BusinessService service = serviceService.read(request.getServiceId());

        if (business == null) {
            throw new RuntimeException("Business not found");
        }

        Booking booking = new Booking.Builder()
                .setCustomer(customer)
                .setBusinessProfile(business)
                .setService(service)
                .setRequestedDate(request.getDate())
                .setStatus(BookingStatus.PENDING)
                .build();

        return bookingService.create(booking);
    }

    // Used by both the customer and business-owner dashboards — whichever
    // side the logged-in user is on, they get their relevant bookings.
    @GetMapping("/me")
    public List<Booking> getMine(Authentication authentication) {
        return bookingService.getAllForUser(CurrentUser.id(authentication));
    }

    @PatchMapping("/{bookingId}/status")
    public Booking updateStatus(@PathVariable Long bookingId, @RequestBody UpdateBookingStatusRequest request) {
        Booking existing = bookingService.read(bookingId);
        if (existing == null) {
            throw new RuntimeException("Booking not found");
        }

        Booking updated = new Booking.Builder()
                .copy(existing)
                .setStatus(request.getStatus())
                .build();

        Booking saved = bookingService.update(updated);

        // Auto-message the customer so something actually happens when a
        // status changes, instead of them needing to notice it themselves.
        User businessOwner = saved.getBusinessProfile().getOwner();
        User customer = saved.getCustomer();
        String statusText = request.getStatus().name().toLowerCase();

        Message notification = new Message.Builder()
                .setSender(businessOwner)
                .setReceiver(customer)
                .setContent("Your booking for " + saved.getRequestedDate() + " has been " + statusText + ".")
                .build();

        messageService.create(notification);

        return saved;
    }

    @GetMapping("/getAll")
    public List<Booking> getAll() {
        return bookingService.getAll();
    }
}
