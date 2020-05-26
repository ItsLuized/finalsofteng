package com.softeng.finalsofteng.repository;

import com.softeng.finalsofteng.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDriverRepository extends JpaRepository<Driver, Long> {
}
