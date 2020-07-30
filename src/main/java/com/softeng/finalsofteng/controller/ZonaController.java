package com.softeng.finalsofteng.controller;

import com.softeng.finalsofteng.model.User;
import com.softeng.finalsofteng.model.Zona;
import com.softeng.finalsofteng.repository.IUserRepository;
import com.softeng.finalsofteng.repository.IZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ZonaController {

    private final IZonaRepository zonaRepository;
    private final IUserRepository userRepository;

    @Autowired
    public ZonaController(IZonaRepository zonaRepository, IUserRepository userRepository) {
        this.zonaRepository = zonaRepository;
        this.userRepository = userRepository;
    }

    //REGISTRO
    @GetMapping("/crearusuario")
    public String registrarUsuario(Model model) {
        User newUser = new User();
        model.addAttribute("newUser", newUser);
        List<Zona> zonas = zonaRepository.findAll();
        model.addAttribute("zonasExistentes", zonas);

        return "NewUsuario";
    }

    @GetMapping("/listausuarios")
    public String listarUsuariosCiudad(Model model) {
        Zona zona = new Zona();
        model.addAttribute("zona", zona);
        List<Zona> listaDeZonas = zonaRepository.findAll();
        model.addAttribute("listaDeZonas", listaDeZonas);

        List<User> listaDeUsuarios = userRepository.findAll();
        model.addAttribute("listaDeUsuarios", listaDeUsuarios);

        return "ListarUsuariosporZona";
    }

    //LOCALIDAD
    @GetMapping("/localidad")
    public String crearLocalidad(Model model) {
        Zona zonaLocalidad = new Zona();
        model.addAttribute("zonaLocalidad", zonaLocalidad);
        List<Zona> zonas = zonaRepository.findAll();
        model.addAttribute("zonasExistentes", zonas);
        return "NewLocalidad";
    }

    @PostMapping("/ciudad") //Esta dirección de aquí tiene que estar en el Action del Form en el HTML
    public String saveCiudad(@ModelAttribute("zonaCiudad") Zona zonaCiudad) {
        Zona zona = new Zona(zonaCiudad.getNombreLugar());
        zona.setZonaPadre(null);
        zonaRepository.save(zona);
        return "redirect:/menu-usuario";
    }

    @PostMapping("/localidad") //Esta dirección de aquí tiene que estar en el Action del Form en el HTML
    public String saveLocalidad(@ModelAttribute("zonaLocalidad") Zona zonaLocalidad) {
        Zona zona = new Zona(zonaLocalidad.getNombreLugar());
        zona.setZonaPadre(zonaLocalidad.getZonaPadre());
        zonaRepository.save(zona);

        return "redirect:/menu-usuario";
    }


}
