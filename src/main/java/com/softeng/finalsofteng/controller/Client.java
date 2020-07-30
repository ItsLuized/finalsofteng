package com.softeng.finalsofteng.controller;

import com.softeng.finalsofteng.model.Driver;
import com.softeng.finalsofteng.model.Role;
import com.softeng.finalsofteng.model.User;
import com.softeng.finalsofteng.model.Zona;
import com.softeng.finalsofteng.repository.IDriverRepository;
import com.softeng.finalsofteng.repository.IZonaRepository;
import com.softeng.finalsofteng.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class Client {

    private final IDriverRepository driverRepository;
    private final IUserService userService;
    private final IZonaRepository zonaRepository;

    @Autowired
    public Client(IDriverRepository driverRepository, IUserService userService, IZonaRepository zonaRepository) {
        this.driverRepository = driverRepository;
        this.userService = userService;
        this.zonaRepository = zonaRepository;
    }

    @GetMapping("/menu-usuario")
    public String menuUsuario() {
        return "MenuUsuario";
    }


    @PostMapping("/crearusuario")
    public String saveUsuario(@ModelAttribute("newUser") User newUser) {
        userService.registerUser(newUser);
        return "redirect:/menu-usuario";
    }

    @GetMapping("/conductor")
    public String crearConductor(Model model) {
        Driver driver = new Driver();
        model.addAttribute("driver", driver);
        return "NewConductor";
    }

    @PostMapping("/conductor") //Esta dirección de aquí tiene que estar en el Action del Form en el HTML
    public String saveConductor(@ModelAttribute("driver") Driver driver) {
        driverRepository.save(driver);
        return "redirect:/menu-usuario";
    }

    @GetMapping("/ciudad")
    public String crearCiudad(Model model) {
        Zona zonaCiudad = new Zona();
        model.addAttribute("zonaCiudad", zonaCiudad);
        return "NewCiudad";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@ModelAttribute("user") User user) {
        User user1 = new User(user.getEmail(), user.getPassword(), user.getDireccion(), user.getDocumento(), user.getTelefono(), user.getZona(), Role.USER);
        userService.registerUser(user1);
        return new ResponseEntity<>(user1, HttpStatus.CREATED);
    }

}

