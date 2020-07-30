package com.softeng.finalsofteng;

import com.softeng.finalsofteng.model.Role;
import com.softeng.finalsofteng.model.User;
import com.softeng.finalsofteng.model.Zona;
import com.softeng.finalsofteng.repository.IUserRepository;
import com.softeng.finalsofteng.repository.IZonaRepository;
import com.softeng.finalsofteng.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PruebasIntegracionySistema {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IZonaRepository zonaRepository;
    @Autowired
    private IUserService userService;

    /**
     * Composite agregar una localidad
     */
    @Test
    public void ZonesBeforeInsertPlusOneIsEqualToZonesAfterInsert() {

        try {
            // Before
            List<Zona> zonas = zonaRepository.findAll();
            int zonasBeforeInsert = zonas.size();

            // During
            zonaRepository.save(new Zona("Melgar"));

            // After
            List<Zona> zonasAfter = zonaRepository.findAll();
            int zonasAfterInsert = zonasAfter.size();

            // assertThat(zonasAfterInsert).isEqualTo(zonasBeforeInsert + 1);
            assertEquals(zonasBeforeInsert + 1, zonasAfterInsert);


        } catch (Exception e) {
            e.printStackTrace();
            fail("No se agrego la ciudad");
        }

    }

    /**
     * Crear usuario
     */
    @Test
    public void UsersBeforeInsertPlusOneIsEqualToUsersAfterInsert() {
        try {
            int usersbeforeInsert = userRepository.findAll().size();
            Zona zona = zonaRepository.findByNombreLugar("Bogotá");
            userService.registerUser(new User("testUser1", "1234", "Direccion", "123456789", "3000000000", zona, Role.USER));
            int usersAfterInsert = userRepository.findAll().size();

            assertEquals(usersbeforeInsert + 1, usersAfterInsert);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Registrar usuario ");
        }
    }


    /**
     * Comprobar que clave es hasheada antes de insert en BD
     */
    @Test
    public void PasswordUsedToRegisterIsHashedPasswordWhenReturned() {

        try {
            Zona zona = zonaRepository.findByNombreLugar("Bogotá");
            User newUser = new User("testUser", "1234", "Direccion", "123456789", "3000000000", zona, Role.USER);
            userService.registerUser(newUser);
            User newUserSearchedInDB = userRepository.findByEmail("testUser");

            assertNotEquals(newUser.getPassword(), newUserSearchedInDB.getPassword());
        } catch (Exception e) {
            fail("Password is the same");
        }

    }


    //listar usuarios de ciudad

    /**
     * Composite listar usuarios
     */
    @Test
    public void UserSearchedByEmailIsEqualToUserSearchedByZone() {
        try {
            Zona barranquilla = zonaRepository.findByNombreLugar("Barranquilla");
            if (barranquilla == null) {
                // si no existe la zona, crearemos un contenedor solo para este usuario
                barranquilla = zonaRepository.save(new Zona("Barranquilla"));
            } else userRepository.deleteAllByZona(barranquilla);

            // Ahora crear un usuario para que este en la zona
            userService.registerUser(new User("usuarioBarranquilla", "pass", "Barranquilla", "1325413", "3200000000", barranquilla, Role.USER));

            List<User> usuarioBarranquilla = userRepository.findAllByZona(barranquilla);
            User userBarranquilla = usuarioBarranquilla.get(0);
            assertEquals(userRepository.findByEmail("usuarioBarranquilla"), userBarranquilla);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Listar usuarios de ciudad ");
        }

    }

}
