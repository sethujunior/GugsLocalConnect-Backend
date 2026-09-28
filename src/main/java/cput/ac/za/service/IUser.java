package cput.ac.za.service;

import cput.ac.za.domain.User;
import cput.ac.za.dto.CustomerSignupRequest;

public interface IUser extends IService<User, Long> {

    User createCustomer(CustomerSignupRequest request);
    User createbusinessOwner(User user);
    User login(String email, String password);
}
