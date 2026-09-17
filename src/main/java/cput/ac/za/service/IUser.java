package cput.ac.za.service;

import cput.ac.za.domain.User;

public interface IUser extends IService<User ,Long> {

    User createCustomer(User user);
}
