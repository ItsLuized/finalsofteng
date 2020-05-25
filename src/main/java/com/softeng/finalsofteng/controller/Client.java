package com.softeng.finalsofteng.controller;

import com.softeng.finalsofteng.model.Zona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, WebMvcAutoConfiguration.class })
@RestController
public class Client {
    private Proxy proxy = Proxy.getInstance();
    private Facade facade = Facade.getInstance();
    private Encryption encryption;


    @GetMapping("/")
    public ResponseEntity<?> home() {
        return new ResponseEntity<>("Sirve", HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> registerUser(String email, String password, String direccion, String documento, String telefono, Zona zona) {
        this.proxy.registerUser(email, password, direccion, documento, telefono, zona);
        return new ResponseEntity<>("200",
                HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> accederSistema(String email, String password, String IP) throws Exception {
        BigInteger nonce = this.proxy.accederSistema(email, password, IP);
        this.encryption = Encryption.getInstance(nonce.toString());
        return new ResponseEntity<>(nonce, HttpStatus.OK);
    }

    @RequestMapping(value = "/noexiste")
    public Object elaborarOperacion(String mesgNotEncrypted, String ip) throws Exception {
        return this.facade.elaborarOperacion(this.encryption.encrypt(mesgNotEncrypted), ip);
    }

    @GetMapping(value = "/zonas")
    public ResponseEntity<?> getZonas() {
        return new ResponseEntity<>(this.proxy.getZonasString(), HttpStatus.OK);
    }

    @GetMapping(value = "/existezona")
    public ResponseEntity<?> existeZona(String nombreLugar) {
        if (this.proxy.existeZona(nombreLugar) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(HttpStatus.FOUND);
    }

}
