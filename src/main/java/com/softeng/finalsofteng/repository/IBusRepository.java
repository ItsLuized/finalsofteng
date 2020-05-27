package com.softeng.finalsofteng.repository;

import com.softeng.finalsofteng.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBusRepository extends JpaRepository<Bus, Long> {

    List<Bus> findAll();

}
