package com.softeng.finalsofteng.repository;

import com.softeng.finalsofteng.model.User;
import com.softeng.finalsofteng.model.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    User findByEmail (String email);

    User findByDocumento (String documento);

    List<User> findAllByZona (Zona zona);

    void deleteAllByZona (Zona zona);

}
