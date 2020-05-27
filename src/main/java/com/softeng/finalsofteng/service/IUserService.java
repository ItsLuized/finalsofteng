package com.softeng.finalsofteng.service;

import com.softeng.finalsofteng.model.User;

public interface IUserService {

    /**
     * Registration process
     * @param email user's email
     * @param password user's password
     * @param direccion user's address
     * @param documento user's id
     * @param telefono user's phone number
     * @param nombreLugar Zone's name
     * @return User if the user was created successfuly
     *         Null if the email is already in use.
     */
    User registerUser(String email, String password,
                      String direccion, String documento,
                      String telefono, String nombreLugar);

    /**
     *
     */
    User updateUser(User user, User updatedUser);

}
