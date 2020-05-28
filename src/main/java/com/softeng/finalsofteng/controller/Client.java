package com.softeng.finalsofteng.controller;

import com.softeng.finalsofteng.model.Bus;
import com.softeng.finalsofteng.model.Driver;
import com.softeng.finalsofteng.model.Route;
import com.softeng.finalsofteng.model.Zona;
import com.softeng.finalsofteng.repository.IBusRepository;
import com.softeng.finalsofteng.repository.IDriverRepository;
import com.softeng.finalsofteng.repository.IUserRepository;
import com.softeng.finalsofteng.repository.IZonaRepository;
import com.softeng.finalsofteng.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;


@Controller
public class Client {

    @Autowired
    private Proxy proxy;
    private Encryption encryption;
    @Autowired
    private IZonaRepository zonaRepository;
    @Autowired
    private IAuthService authService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IDriverRepository driverRepository;
    @Autowired
    private IBusRepository busRepository;


    @GetMapping("/")
    public ResponseEntity<?> home() {
        return new ResponseEntity<>("Sirve", HttpStatus.OK);
    }

    /*
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

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> accederSistema(@RequestParam String email, @RequestParam String password) throws Exception {
        Authentication authentication = authService.getAuthentication();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(authentication.getCredentials().toString() + "\n");
        System.out.println(passwordEncoder.matches(authentication.getCredentials().toString(), encodedPassword));
        System.out.print(encodedPassword);
        User user = userRepository.findByEmail(authentication.getName());
        if (user != null && authentication.getCredentials().toString().equals(user.getPassword())) {
            authentication.setAuthenticated(true);
            return new ResponseEntity<>("Logeado", HttpStatus.OK);
        } else return new ResponseEntity<>("No Logeado", HttpStatus.NOT_FOUND);
        //authentication.setAuthenticated(false);

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        Authentication authentication = authService.getAuthentication();
        authentication.setAuthenticated(false);
        return new ResponseEntity<>("Cerro sesion", HttpStatus.OK);
    }


    @RequestMapping("/noexiste")
    public Object elaborarOperacion(String mesgNotEncrypted, String ip) throws Exception {
        return this.facade.elaborarOperacion(this.encryption.encrypt(mesgNotEncrypted), ip);
    }



    @GetMapping("/zonas")
    public ResponseEntity<?> getZonas() {
        return new ResponseEntity<>(this.proxy.getZonasString(), HttpStatus.OK);
    }

    @GetMapping("/existezona")
    public ResponseEntity<?> existeZona(String nombreLugar) {
        if (this.proxy.existeZona(nombreLugar) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(HttpStatus.FOUND);
    }

    @PostMapping("/localidadaciudad")
    public ResponseEntity<?> agregarLocalidadACiudad(@RequestParam String nombreLocalidad, @RequestParam String nombreCiudad) {
        if (nombreCiudad == "" || nombreLocalidad == "")
            return new ResponseEntity<>("ERROR: nombre de la ciudad o nombre de localidad faltante", HttpStatus.NOT_FOUND);

        Zona zonaPadre = zonaRepository.findByNombreLugar(nombreCiudad);
        this.proxy.crearContenedor(nombreLocalidad, zonaPadre);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PostMapping("/bus")
    public ResponseEntity<?> createBus(@RequestParam String placa, @RequestParam int capacidad,
                                       @RequestParam String marca, @RequestParam String driverName,
                                       @RequestParam String route) {
        Driver driver = driverRepository.findByName(driverName);
        this.proxy.crearBus(placa, capacidad, marca, driver, route);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    @GetMapping("/bus")
    public ResponseEntity<?> getAllBuses() {
        return new ResponseEntity<>(busRepository.findAll(), HttpStatus.OK);
    }


    @PostMapping("/bus/conductor")
    public ResponseEntity<?> createBusDriver(@RequestParam String nombre, @RequestParam String apellido,
                                             @RequestParam int edad) {
        Driver driver = new Driver(nombre, apellido, edad);
        driverRepository.save(driver);
        return new ResponseEntity<>(driver, HttpStatus.CREATED);
    }

    //@GetMapping("/menu") Anotacion para el menu

    //@GetMapping("/menubus") Anotacion para el menuBus

    //@GetMapping("/menuusuario") Anotacion para el menuUsuario

    @PostMapping("/ciudad")
    public ResponseEntity<?> addCiudad(@RequestParam String nombreCiudad) {
        if (nombreCiudad == "")
            return new ResponseEntity<>("ERROR: nombre de la localidad faltante", HttpStatus.NOT_FOUND);

        this.proxy.crearContenedor(nombreCiudad, null);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/localidad")
    public ResponseEntity<?> addLocalidad(@RequestParam String nombreLocalidad, @RequestParam String nombreCiudad) {
        if (nombreLocalidad == "" || nombreCiudad == "")
            return new ResponseEntity<>("ERROR: nombre de la localidad o nombre ciudad faltante", HttpStatus.NOT_FOUND);

        Zona zonaPadre = zonaRepository.findByNombreLugar(nombreCiudad);
        this.proxy.crearContenedor(nombreLocalidad, zonaPadre);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    */


    /*---------------------------------------------------------------------------*/


    //Logica para los Menus
    @GetMapping("/menu")
    public String menuPrincial() {
        return "Menu";
    }


    @GetMapping("/ListaBus")
    public String ListarBuses(Model model) {
        List<Bus> ListadeBuses = busRepository.findAll();
        model.addAttribute("ListadeBuses", ListadeBuses);
        return "ListaBuses";
    }

    //Logica para manejar creación de conductores
    @GetMapping("/conductor")
    public String crearConductor(Model model) {
        Driver driver = new Driver();
        model.addAttribute("driver", driver);
        return "NewConductor";
    } //Esto esta llamando al template NewConductor

    @PostMapping("/conductor") //Esta dirección de aquí tiene que estar en el Action del Form en el HTML
    public String saveConductor(@ModelAttribute("driver") Driver driver) {
        driverRepository.save(driver);
        return "redirect:/";
    }

    //Logica para manejar creación de Ciudades
    @GetMapping("/ciudad")
    public String crearCiudad(Model model) {
        Zona zonaCiudad = new Zona();
        model.addAttribute("zonaCiudad", zonaCiudad);
        return "NewCiudad";
    }

    @PostMapping("/ciudad") //Esta dirección de aquí tiene que estar en el Action del Form en el HTML
    public String saveCiudad(@ModelAttribute("zonaCiudad") Zona zonaCiudad) {
        this.proxy.crearContenedor(zonaCiudad.getNombreLugar(), null);

        return "redirect:/";
    }

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
        //zonaRepository.save(zonaLocalidad);

        Zona zonaPadre = zonaLocalidad.getZonaPadre();
        this.proxy.crearContenedor(zonaLocalidad.getNombreLugar(), zonaPadre);


        return "redirect:/";
    }


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

    @PostMapping("/bus") //Esta dirección de aquí tiene que estar en el Action del Form en el HTML
    public String saveBus(@ModelAttribute("nuevoBus") Bus nuevoBus) {
        busRepository.save(nuevoBus);
        /*Driver driver = nuevoBus.getDriver();
        Route route = nuevoBus.getRoute();
        this.proxy.crearBus(nuevoBus.getPlaca(), nuevoBus.getCapacidad(), nuevoBus.getMarca(), driver, route.toString());*/

        return "redirect:/";

    }
}

