package cput.ac.za.service;

import cput.ac.za.domain.User;

public interface IUser extends IService<User ,Long> {

    User createCustomer(User user);
    User createbusinessOwner(User user);
    User login(String email, String password);
}
