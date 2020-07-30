package com.softeng.finalsofteng.controller;

import com.softeng.finalsofteng.model.Zona;
import com.softeng.finalsofteng.repository.IZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Facade {

    private final IZonaRepository zonaRepository;

    @Autowired
    public Facade(IZonaRepository zonaRepository) {
        this.zonaRepository = zonaRepository;
    }

    public void crearContenedor(String nombreLugar, Zona zonaPadre){
        Zona zona = new Zona(nombreLugar);
        zona.setZonaPadre(zonaPadre);
        zonaRepository.save(zona);
    }
}
