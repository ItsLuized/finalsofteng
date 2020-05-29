package com.softeng.finalsofteng.service.impl;

import com.softeng.finalsofteng.model.Role;
import com.softeng.finalsofteng.model.User;
import com.softeng.finalsofteng.repository.IUserRepository;
import com.softeng.finalsofteng.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user, User updatedUser) {
        if (updatedUser.getPassword() != null)
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        if (updatedUser.getDireccion() != null)
            user.setDireccion(updatedUser.getDireccion());
        if (updatedUser.getDocumento() != null)
            user.setDocumento(updatedUser.getDocumento());
        if (updatedUser.getTelefono() != null)
            user.setTelefono(updatedUser.getTelefono());
        if (updatedUser.getZona() != null)
            user.setZona(updatedUser.getZona());

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByDocumento(String documento) {
        return userRepository.findByDocumento(documento);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null)
            // return null;
            throw new UsernameNotFoundException("User was not found in DB");
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Role role = user.getRole();
        grantedAuthorities.add(new SimpleGrantedAuthority(role.toString()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), grantedAuthorities);
    }
}
