package com.softeng.finalsofteng.repository;

import com.softeng.finalsofteng.model.Zona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IZonaRespository extends JpaRepository<Zona, Long> {

    Zona findByNombreLugar (String nombreLugar);

    List<Zona> findAll ();

}
