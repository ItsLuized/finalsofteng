package com.softeng.finalsofteng.repository;

import com.softeng.finalsofteng.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDriverRepository extends JpaRepository<Driver, Long> {

    Driver findByName (String name);

}
