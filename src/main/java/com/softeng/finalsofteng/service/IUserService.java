package com.softeng.finalsofteng.service;

import com.softeng.finalsofteng.model.User;

import java.util.List;

public interface IUserService {

    User registerUser(User user);

    User updateUser(User user, User updatedUser);

    List<User> getAllUsers();

    User findByEmail(String email);

    User findByDocumento(String documento);

}
