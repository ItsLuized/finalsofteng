package com.softeng.finalsofteng.controller;

import com.softeng.finalsofteng.model.Zona;
import com.softeng.finalsofteng.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;


@Controller
public class Client {

    private final Proxy proxy = Proxy.getInstance();
    private final Facade facade = Facade.getInstance();
    private Encryption encryption;

    @Autowired
    private IUserService userService;


    @GetMapping("/")
    public ResponseEntity<?> home() {
        return new ResponseEntity<>("Sirve", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam String email, @RequestParam String password,
                                               @RequestParam String direccion, @RequestParam String documento,
                                               @RequestParam String telefono, @RequestParam String nombreLugar) {
        userService.registerUser(email, password, direccion, documento, telefono, nombreLugar);
        return new ResponseEntity<>("200",
                HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> accederSistema(String email, String password, String IP) throws Exception {
        BigInteger nonce = this.proxy.accederSistema(email, password, IP);
        this.encryption = Encryption.getInstance(nonce.toString());
        return new ResponseEntity<>(nonce, HttpStatus.OK);
    }

    /*
    @RequestMapping("/noexiste")
    public Object elaborarOperacion(String mesgNotEncrypted, String ip) throws Exception {
        return this.facade.elaborarOperacion(this.encryption.encrypt(mesgNotEncrypted), ip);
    }
    */


    @GetMapping("/zonas")
    public ResponseEntity<?> getZonas() {
        return new ResponseEntity<>(this.proxy.getZonasString(), HttpStatus.OK);
    }

    @GetMapping("/existezona")
    public ResponseEntity<?> existeZona(String nombreLugar) {
        if (this.proxy.existeZona(nombreLugar) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(HttpStatus.FOUND);
    }

    /*@PostMapping("/bus")
    public ResponseEntity<?> createBus()
*/
}
