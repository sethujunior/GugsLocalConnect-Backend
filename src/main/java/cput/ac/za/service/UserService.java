package cput.ac.za.service;

import cput.ac.za.domain.Role;
import cput.ac.za.domain.User;
import cput.ac.za.dto.CustomerSignupRequest;
import cput.ac.za.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUser {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User read(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean delete(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User createCustomer(CustomerSignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User newUser = new User.Builder()
                .setName(request.getName())
                .setEmail(request.getEmail())
                .setPasswordHash(passwordEncoder.encode(request.getPassword()))
                .setPhone(request.getPhone())
                .setRole(Role.CUSTOMER)
                .build();

        return userRepository.save(newUser);
    }

    @Override
    public User createbusinessOwner(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User newUser = new User.Builder()
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setPasswordHash(passwordEncoder.encode(user.getPassword()))
                .setPhone(user.getPhone())
                .setRole(Role.BUSINESS_OWNER)
                .build();

        return userRepository.save(newUser);
    }
    public User findByEmailRaw(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return user;
    }
}
