package com.softeng.finalsofteng.repository;

import com.softeng.finalsofteng.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBusRepository extends JpaRepository<Bus, Long> {

    List<Bus> findAll();

}
