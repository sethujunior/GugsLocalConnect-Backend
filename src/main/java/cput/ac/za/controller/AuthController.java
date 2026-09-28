package cput.ac.za.controller;

import cput.ac.za.domain.Role;
import cput.ac.za.domain.User;
import cput.ac.za.dto.AuthResponse;
import cput.ac.za.dto.BusinessSignupRequest;
import cput.ac.za.dto.CustomerSignupRequest;
import cput.ac.za.dto.LoginRequest;
import cput.ac.za.security.JwtUtil;
import cput.ac.za.service.BusinesProfileService;
import cput.ac.za.service.UserService;
import org.springframework.web.bind.annotation.*;

// Matches the /api/auth/* endpoints the Angular AuthService calls, and
// the "User Management" API module (registration, login, JWT) in the
// architecture diagram.
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final BusinesProfileService businessProfileService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, BusinesProfileService businessProfileService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.businessProfileService = businessProfileService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register/customer")
    public AuthResponse registerCustomer(@RequestBody CustomerSignupRequest request) {
        User created = userService.createCustomer(request);
        return new AuthResponse(jwtUtil.generateToken(created), created);
    }

    @PostMapping("/register/business")
    public AuthResponse registerBusiness(@RequestBody BusinessSignupRequest request) {
        // Creates the User (role = BUSINESS_OWNER) and the linked
        // BusinessProfile together, then logs them straight in.
        var profile = businessProfileService.signupBusiness(request);
        return new AuthResponse(jwtUtil.generateToken(profile.getOwner()), profile.getOwner());
    }

    @PostMapping("/login/customer")
    public AuthResponse loginCustomer(@RequestBody LoginRequest request) {
        User user = userService.login(request.getEmail(), request.getPassword());
        if (user.getRole() != Role.CUSTOMER) {
            throw new RuntimeException("This account is not a customer account");
        }
        return new AuthResponse(jwtUtil.generateToken(user), user);
    }

    @PostMapping("/login/business")
    public AuthResponse loginBusiness(@RequestBody LoginRequest request) {
        User user = userService.login(request.getEmail(), request.getPassword());
        if (user.getRole() != Role.BUSINESS_OWNER) {
            throw new RuntimeException("This account is not a business account");
        }
        return new AuthResponse(jwtUtil.generateToken(user), user);
    }
}
