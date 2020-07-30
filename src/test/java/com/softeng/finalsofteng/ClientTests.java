package com.softeng.finalsofteng;

import com.softeng.finalsofteng.model.Zona;
import com.softeng.finalsofteng.repository.IZonaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClientTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IZonaRepository zonaRepository;

    @Before
    public void Setup() {
        zonaRepository.deleteAll();
        zonaRepository.save(new Zona("Bogotá"));
    }

    /**
     * Adds a localidad to a City
     *
     * @throws Exception If test fails
     */
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void addZonetoZonePadre() throws Exception {
        String zonaid = String.valueOf(zonaRepository.findByNombreLugar("Bogotá").getZonaId());
        mockMvc.perform(post("/localidad")
                .param("zonaLocalidad.nombreLugar", "Kennedy")
                .param("zona.getZonaId()", zonaid))
                .andExpect(redirectedUrl("/menu-usuario"));
    }

    /**
     * @throws Exception If test fails
     */
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void listarUsuariosPorLocalidadCiudad() throws Exception {
        mockMvc.perform(get("/listausuarios"))
                .andExpect(content().string(containsString("Listar Usuarios por Zona")));
    }

    // Have to Make it work
    /*@Test
    public void RegisterUserAndExpectCreatedResponse() throws Exception {
        String zonaid = String.valueOf(zonaRepository.findByNombreLugar("Bogotá").getZonaId());
        System.out.println(zonaid);
        mockMvc.perform(post("/register")
                .param("newUser.email", "correo.prueba@correo.com")
                .param("newUser.password", "Contrasena")
                .param("newUser.direccion", "direccionPrueba")
                .param("newUser.documento", "6151312")
                .param("newUser.telefono", "651651321681")
                .param("newUser.zona", zonaid)).andExpect(status().isCreated());

    }*/

}
