package com.softeng.finalsofteng.controller;

import com.softeng.finalsofteng.model.Bus;
import com.softeng.finalsofteng.model.Driver;
import com.softeng.finalsofteng.model.User;
import com.softeng.finalsofteng.model.Zona;
import com.softeng.finalsofteng.repository.IBusRepository;
import com.softeng.finalsofteng.repository.IDriverRepository;
import com.softeng.finalsofteng.repository.IUserRepository;
import com.softeng.finalsofteng.repository.IZonaRepository;
import com.softeng.finalsofteng.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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


    //CAMBIO NUEVO HECHO POR JUANSE PARA LISTAR BUSES
    @GetMapping("/ListaBus")
    public String ListarBuses(Model model){
        List<Bus> ListadeBuses = busRepository.findAll();
        model.addAttribute("ListadeBuses", ListadeBuses);
        return "ListaBuses"; }

     //CAMBIO NUEVO HECHO POR JUANSE PARA LISTAR BUSES

    //CAMBIO NUEVO HECHO POR JUANSE Y NICKY PARA AGREGAR UN BUS

    @RequestMapping("/CrearBus")
    public String CrearUnBus(Model model){

        return "NewBus";
    }

    
    //CAMBIO NUEVO HECHO POR JUANSE Y NICKY PARA AGREGAR UN BUS


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
}
