package com.softeng.finalsofteng.controller;

import com.softeng.finalsofteng.model.*;
import com.softeng.finalsofteng.repository.IUserRepository;
import com.softeng.finalsofteng.repository.IZonaRepository;
import com.softeng.finalsofteng.repository.IBusRepository;
import com.softeng.finalsofteng.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Facade implements ILogin {
    private static Facade instance = null;
    private final ArrayList<Bus> buses;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IZonaRepository zonaRepository;
    @Autowired
    private IBusRepository busRepository;
    @Autowired
    private IUserService userService;


    private Facade() {
        this.buses = new ArrayList<>();
    }

    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    public void crearBus(Bus bus) {
        busRepository.save(bus);
    }

    private String verRutas() {
        List<Bus> buses = busRepository.findAll();
        return  buses.toString();
    }

    public void crearContenedor(String nombreLugar, Zona zonaPadre){
        Zona zona = new Zona(nombreLugar);
        zona.setZonaPadre(zonaPadre);
        zonaRepository.save(zona);
    }

    private String adicionarUsuarioaComposite(String nombreLugar, String documento){
        String retorno = "fallo";
        User user = userRepository.findByDocumento(documento);
        if (user == null){
            retorno = "void";
        }
        Zona zona = zonaRepository.findByNombreLugar(nombreLugar);
        user.setZona(zona);
        userRepository.save(user);
        return retorno;
    }

    private String listarUsuarios(String nombreLugar){
        Zona zona = zonaRepository.findByNombreLugar(nombreLugar);
        List<User> usersInZone = userRepository.findAllByZona(zona);
        return usersInZone.toString();
    }

    @Override
    public User registerUser(String email, String password, String direccion, String documento, String telefono, Zona zona) {
        zonaRepository.save(zona);
        User user = new User(email, password, direccion, documento, telefono, zona, Role.USER);
        return userService.registerUser(user);
    }

    public String getZonasString() {

        List<Zona> zonas = zonaRepository.findAll();

        StringBuilder stringBuilder = new StringBuilder();
        for (Zona zona : zonas) {
            stringBuilder.append(zona.getNombreLugar() + "\n");
        }
        return stringBuilder.toString();
    }

    public Zona existeZona(String nombreLugar) {
        return zonaRepository.findByNombreLugar(nombreLugar);

    }
}
