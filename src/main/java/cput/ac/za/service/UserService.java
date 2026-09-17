package cput.ac.za.service;

import cput.ac.za.domain.Role;
import cput.ac.za.domain.User;
import cput.ac.za.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUser{

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User read(Long Id) {
        return userRepository.findById(Id).orElse(null);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean delete(Long Id) {
        if (userRepository.existsById(Id)) {
            userRepository.deleteById(Id);
            return true;
        }
        return false;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User createCustomer(User user) {

        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Automatically make the new account a CUSTOMER
        User newUser = new User.Builder()
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setPasswordHash(user.getPassword())
                .setPhone(user.getPhone())
                .setRole(Role.CUSTOMER)
                .build();

        return userRepository.save(newUser);
    }

    @Override
    public User createbusinessOwner(User user) {

        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Automatically make the new account a BUSINESS_OWNER
        User newUser = new User.Builder()
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setPasswordHash(user.getPassword())
                .setPhone(user.getPhone())
                .setRole(Role.BUSINESS_OWNER)
                .build();

        return userRepository.save(newUser);
    }
    @Override
    public User login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getPassword() == null) {
            throw new RuntimeException("User password is null");
        }

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Incorrect password");
        }

        return user;
    }
}
