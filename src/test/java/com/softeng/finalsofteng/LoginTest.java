package com.softeng.finalsofteng;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void withValidUserPasswordLoginRedirectsToMainMenu() throws Exception {
        mockMvc.perform(post("/login")
                .param("username", "luismacr")
                .param("password", "luis"))
                // Si el login es exitoso, envia al menu principal que tiene la url '/'
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void withInvalidCredentialsRedirectToLoginWithErrorMessage() throws Exception {
        mockMvc.perform(post("/login")
                .param("username", "luismacr")
                // Le mandamos una mala contraseña
                .param("password", "badpassword"))
                // Si las credenciales son incorrectas, me manda un al mismo login pero con un error
                .andExpect(redirectedUrl("/login?error"));
    }
}
