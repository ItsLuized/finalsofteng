package com.softeng.finalsofteng.model;

public interface ILogin {

    User registerUser(String email, String password, String direccion, String documento, String telefono, Zona zona);
}
