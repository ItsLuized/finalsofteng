package com.softeng.finalsofteng.controller;

import com.softeng.finalsofteng.model.*;
import com.softeng.finalsofteng.repository.IUserRepository;
import com.softeng.finalsofteng.repository.IZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Component
public class Proxy implements ILogin {
    private static Proxy instance = null;
    private final Map<String, User> users;
    @Autowired
    private Facade facade;
    private final Map<String, Zona> zonas;
    private BigInteger nonce;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IZonaRepository zonaRespository;

    private Proxy() {
        this.users = new HashMap();
        this.zonas = new HashMap<>();
        this.nonce = new BigInteger("0");
    }

    public static Proxy getInstance() {
        if (instance == null) {
            instance = new Proxy();
        }
        return instance;
    }

    public User registerUser(String email, String password, String direccion, String documento, String telefono, Zona zona) {
        return this.facade.registerUser(email, password, direccion, documento, telefono, zona);
    }

    public Map<String, Zona> getZonas() {
        return zonas;
    }

    public String getZonasString() {
        return this.facade.getZonasString();
    }

    public Zona existeZona(String nombreLugar) {
        return facade.existeZona(nombreLugar);
    }


    public void crearContenedor(String nombreLugar, Zona zonaPadre){
        this.facade.crearContenedor(nombreLugar, zonaPadre);
    }

    public void crearBus(Bus bus) {
        this.facade.crearBus(bus);
    }

    /*public BigInteger getNonceByIP(String ip){
        return this.ipNonce.get(ip);
    }*/

    /*
    public String agregarUsuarioAZona(String nombreLugar, String documento){
        String retorno = "fallo";
        User user = userRepository.findByDocumento(documento);
        if (user == null){
            retorno = "void";
        }
        Zona zona = zonaRespository.findByNombreLugar(nombreLugar);
        user.setZona(zona);
        userRepository.save(user);
        return retorno;
    }
    */

    public void remplazarUsuario(User oldUser, User newUser){
        this.users.remove(oldUser.getEmail());
        this.users.put(newUser.getEmail(), newUser);
    }

    public Map<String, User> getUsers() {
        return users;
    }
}
