package com.softeng.finalsofteng;

import com.softeng.finalsofteng.controller.Client;
import com.softeng.finalsofteng.controller.Proxy;
import com.softeng.finalsofteng.model.Role;
import com.softeng.finalsofteng.model.User;
import com.softeng.finalsofteng.model.Zona;
import com.softeng.finalsofteng.repository.IBusRepository;
import com.softeng.finalsofteng.repository.IUserRepository;
import com.softeng.finalsofteng.repository.IZonaRepository;
import com.softeng.finalsofteng.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PruebasIntegracionySistema {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private IBusRepository busRepository;
    @Autowired
    private IZonaRepository zonaRepository;
    @Autowired
    private Proxy proxy;


    /**
     * Composite agregar una localidad
     */
    @Test
    public void agregarunaLocalidad() {

        try {
            // Before
            List<Zona> zonas = zonaRepository.findAll();
            int zonasBeforeInsert = zonas.size();

            // During
            zonaRepository.save(new Zona("Melgar"));

            // After
            List<Zona> zonasAfter = zonaRepository.findAll();
            int zonasAfterInsert = zonasAfter.size();

            //assertThat(zonasAfterInsert).isEqualTo(zonasBeforeInsert + 1);
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
    public void crearUsuario() {
        try {
            int usersbeforeInsert = userRepository.findAll().size();
            Zona zona = zonaRepository.findByNombreLugar("Bogotá");
            this.proxy.registerUser("testUser1", "1234", "Direccion", "123456789", "3000000000", zona);
            User newUserSearchedInDB = userRepository.findByEmail("testUser");
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
    public void hashedPassword() {

        try {
            Zona zona = zonaRepository.findByNombreLugar("Bogotá");
            User newUser = new User("testUser", "1234", "Direccion", "123456789", "3000000000", zona, Role.USER);
            this.proxy.registerUser(newUser.getEmail(), newUser.getPassword(), newUser.getDireccion(), newUser.getDocumento(), newUser.getTelefono(), newUser.getZona());
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
    public void listarUsuarioaCiudad() {
        try {
            Zona barranquilla = zonaRepository.findByNombreLugar("Barranquilla");
            if (barranquilla == null) {
                // si no existe la zona, crearemos un contenedor solo para este usuario
                barranquilla = zonaRepository.save(new Zona("Barranquilla"));
            } else userRepository.deleteAllByZona(barranquilla);

            // Ahora crear un usuario para que este en la zona
            proxy.registerUser("usuarioBarranquilla", "pass", "Barranquilla", "1325413", "3200000000", barranquilla);


            List<User> usuarioBarranquilla = userRepository.findAllByZona(barranquilla);
            User userBarranquilla = usuarioBarranquilla.get(0);
            assertEquals(userRepository.findByEmail("usuarioBarranquilla"), userBarranquilla);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Listar usuarios de ciudad ");
        }

    }

}
