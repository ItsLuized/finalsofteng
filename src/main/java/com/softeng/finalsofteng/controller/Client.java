package com.softeng.finalsofteng.controller;

import com.softeng.finalsofteng.model.*;
import com.softeng.finalsofteng.repository.IBusRepository;
import com.softeng.finalsofteng.repository.IDriverRepository;
import com.softeng.finalsofteng.repository.IUserRepository;
import com.softeng.finalsofteng.repository.IZonaRepository;
import com.softeng.finalsofteng.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;


@Controller
public class Client {

    @Autowired
    private Proxy proxy;
    @Autowired
    private IZonaRepository zonaRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IDriverRepository driverRepository;
    @Autowired
    private IBusRepository busRepository;


    @GetMapping("/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken))
            return "Menu";

        // if it is not authenticated, then go to the Pantalla Inicio
        return "PantallaInicio";
    }

    // There's no need to map POST /login, because Spring Security does it for us
    @GetMapping("/login")
    public String loginPage(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "IniciarSesion";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestParam String email, @RequestParam String password,
                                          @RequestParam String direccion, @RequestParam String documento,
                                          @RequestParam String telefono, @RequestParam String nombreLugar) {
        Zona zona = zonaRepository.findByNombreLugar(nombreLugar);
        if (zona == null)
            return new ResponseEntity<>("Zona no existente.", HttpStatus.NOT_FOUND);
        User user = this.proxy.registerUser(email, password, direccion, documento, telefono, zona);
        return new ResponseEntity<>(user,
                HttpStatus.CREATED);
    }

    //LÓGICA DE MENUS
    @GetMapping("/menu-usuario")
    public String menuUsuario() {
        return "MenuUsuario";
    }

    @GetMapping("/menu-bus")
    public String menuBus() {
        return "MenuBus";
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

    @PostMapping("/crearusuario")
    public String saveUsuario(@ModelAttribute("newUser") User newUser) {
        this.proxy.registerUser(newUser.getEmail(), newUser.getPassword(), newUser.getDireccion(), newUser.getDocumento(), newUser.getTelefono(), newUser.getZona());
        //serRepository.save(newUser);
        return "redirect:/menu-usuario";
    }

    //LISTAS
    @GetMapping("/listabus")
    public String listarBuses(Model model) {
        List<Bus> listaDeBuses = busRepository.findAll();
        model.addAttribute("listaDeBuses", listaDeBuses);
        return "ListaBuses";
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


    //CONDUCTORES
    @GetMapping("/conductor")
    public String crearConductor(Model model) {
        Driver driver = new Driver();
        model.addAttribute("driver", driver);
        return "NewConductor";
    } //Esto esta llamando al template NewConductor

    @PostMapping("/conductor") //Esta dirección de aquí tiene que estar en el Action del Form en el HTML
    public String saveConductor(@ModelAttribute("driver") Driver driver) {
        driverRepository.save(driver);
        return "redirect:/menu-usuario";
    }


    //CIUDAD
    @GetMapping("/ciudad")
    public String crearCiudad(Model model) {
        Zona zonaCiudad = new Zona();
        model.addAttribute("zonaCiudad", zonaCiudad);
        return "NewCiudad";
    }

    @PostMapping("/ciudad") //Esta dirección de aquí tiene que estar en el Action del Form en el HTML
    public String saveCiudad(@ModelAttribute("zonaCiudad") Zona zonaCiudad) {
        this.proxy.crearContenedor(zonaCiudad.getNombreLugar(), null);

        return "redirect:/menu-usuario";
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

    @PostMapping("/localidad") //Esta dirección de aquí tiene que estar en el Action del Form en el HTML
    public String saveLocalidad(@ModelAttribute("zonaLocalidad") Zona zonaLocalidad) {
        Zona zonaPadre = zonaLocalidad.getZonaPadre();
        this.proxy.crearContenedor(zonaLocalidad.getNombreLugar(), zonaPadre);

        return "redirect:/menu-usuario";
    }


    //BUS
    @GetMapping("/bus")
    public String crearBus(Model model) {
        Bus nuevoBus = new Bus();
        model.addAttribute("nuevoBus", nuevoBus);
        List<Driver> driversExistentes = driverRepository.findAll();
        model.addAttribute("driversExistentes", driversExistentes);
        List<Route> routes = Arrays.asList(Route.values());
        model.addAttribute("routes", routes);

        return "NewBus";
    }

    @PostMapping("/bus")
    public String saveBus(@ModelAttribute("nuevoBus") Bus nuevoBus) {
        this.proxy.crearBus(nuevoBus);

        return "redirect:/menu-bus";

    }
}

