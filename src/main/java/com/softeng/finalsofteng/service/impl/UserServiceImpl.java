package com.softeng.finalsofteng.service.impl;

import com.softeng.finalsofteng.model.User;
import com.softeng.finalsofteng.model.Zona;
import com.softeng.finalsofteng.repository.IUserRepository;
import com.softeng.finalsofteng.repository.IZonaRepository;
import com.softeng.finalsofteng.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IZonaRepository zonaRepository;

    @Override
    public User registerUser(String email, String password, String direccion, String documento, String telefono, String nombreLugar) {
        //Zona zona = zonaRepository.findByNombreLugar(nombreLugar);
        Zona zona = new Zona(nombreLugar);
        zonaRepository.save(zona);
        User user = new User(email, password, direccion, documento, telefono, zona);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user, User updatedUser) {
        return null;
    }
}