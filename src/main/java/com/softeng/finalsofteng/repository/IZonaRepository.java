package com.softeng.finalsofteng.repository;

import com.softeng.finalsofteng.model.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IZonaRepository extends JpaRepository<Zona, Long> {

    Zona findByNombreLugar(String nombreLugar);

    List<Zona> findAll();

}
