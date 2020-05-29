package com.softeng.finalsofteng;

import com.softeng.finalsofteng.repository.IZonaRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClientTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IZonaRepository zonaRepository;

    /**
     * Verifies if the url the user is redirected to, when entering correct credentials, is '/' (the main menu)
     *
     * @throws Exception
     */
    @Test
    public void login() throws Exception {
        mockMvc.perform(post("/login")
                .param("username", "luismacr")
                .param("password", "luis"))
                // Si el login es exitoso, envia al menu principal que tiene la url '/'
                .andExpect(redirectedUrl("/"));
    }

    /**
     * Verifies that when the user enters bad credentials, he is redirected to the same '/longin' but with a error
     *
     * @throws Exception
     */
    @Test
    public void errorLogin() throws Exception {
        mockMvc.perform(post("/login")
                .param("username", "luismacr")
                // Le mandamos una mala contraseña
                .param("password", "badpassword"))
                // Si las credenciales son incorrectas, me manda un al mismo login pero con un error
                .andExpect(redirectedUrl("/login?error"));
    }

    /**
     * Adds a localidad to a City
     *
     * @throws Exception
     */
    @Test
    public void addZonetoZonePadre() throws Exception {
        String zonaid = String.valueOf(zonaRepository.findByNombreLugar("Bogotá").getZonaId());
        mockMvc.perform(post("/localidad")
                .param("zonaLocalidad.nombreLugar", "Kennedy")
                .param("zona.getZonaId()", zonaid))
                .andExpect(redirectedUrl("/menu-usuario"));
    }

    /**
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void listarUsuariosPorLocalidadCiudad() throws Exception {
        mockMvc.perform(get("/listausuarios"))
                .andExpect(content().string(containsString("Listar Usuarios por Zona")));
    }

}
