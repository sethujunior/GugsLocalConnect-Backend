package cput.ac.za.controller;

import cput.ac.za.domain.User;
import cput.ac.za.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Plain CRUD over users. Signup and login live in AuthController now,
// since those need to issue JWTs and enforce role-specific rules.
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
}
