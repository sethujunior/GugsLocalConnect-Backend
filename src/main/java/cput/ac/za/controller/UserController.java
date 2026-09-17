package cput.ac.za.controller;

import cput.ac.za.domain.User;
import cput.ac.za.dto.LoginRequest;
import cput.ac.za.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping("/read/{userId}")
    public User read(@PathVariable Long userId) {
        return userService.read(userId);
    }

    @PutMapping("/update")
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping("/delete/{userId}")
    public boolean delete(@PathVariable Long userId) {
        return userService.delete(userId);
    }

    @GetMapping("/getAll")
    public List<User> getAll() {
        return userService.getAll();
    }

    @PostMapping("/create-customer")
    public User createCustomer(@RequestBody User user) {
        return userService.createCustomer(user);
    }

    @PostMapping("/create-businessOwner")
    public User createbusiness(@RequestBody User user) {
        return userService.createbusinessOwner(user);
    }
    @PostMapping("/login")
    public User login(@RequestBody LoginRequest loginRequest) {
        return userService.login(
                loginRequest.getEmail(),
                loginRequest.getPassword());
    }
}


