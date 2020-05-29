package com.softeng.finalsofteng.config;

import com.softeng.finalsofteng.controller.Facade;
import com.softeng.finalsofteng.model.*;
import com.softeng.finalsofteng.repository.IBusRepository;
import com.softeng.finalsofteng.repository.IDriverRepository;
import com.softeng.finalsofteng.repository.IUserRepository;
import com.softeng.finalsofteng.repository.IZonaRepository;
import com.softeng.finalsofteng.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DemoData {

    @Autowired
    private IUserService userService;
    @Autowired
    private IDriverRepository driverRepository;
    @Autowired
    private IBusRepository busRepository;
    @Autowired
    private IZonaRepository zonaRepository;
    @Autowired
    private Facade facade;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {

        // inserting Zonas
        zonaRepository.save(new Zona("Bogotá"));
        zonaRepository.save(new Zona("Chia"));
        zonaRepository.save(new Zona("Cajicá"));
        zonaRepository.save(new Zona("Zipaquirá"));
        Zona zonaPadre = zonaRepository.findByNombreLugar("Bogotá");
        facade.crearContenedor("Usaquen", zonaPadre);
        facade.crearContenedor("Suba", zonaPadre);
        facade.crearContenedor("Chapinero", zonaPadre);

        // Inserting Drivers
        driverRepository.save(new Driver("Rogelio", "Rodriguez", 35));
        driverRepository.save(new Driver("Edgar", "Ramirez", 27));
        driverRepository.save(new Driver("Cristian", "Silva", 42));

        // Inserting Buses
        Driver driver = driverRepository.findByName("Rogelio");
        busRepository.save(new Bus("ASD271", 25, "Volvo", driver, Route.SEPTIMA));
        driver = driverRepository.findByName("Edgar");
        busRepository.save(new Bus("KLG654", 40, "Fiat", driver, Route.AUTONORTE));

        // Inserting Users
        Zona chia = zonaRepository.findByNombreLugar("Chia");
        Zona usaquen = zonaRepository.findByNombreLugar("Usaquen");
        Zona chapinero = zonaRepository.findByNombreLugar("Chapinero");
        Zona suba = zonaRepository.findByNombreLugar("Suba");

        // ADMINS
        userService.registerUser(new User("luismacr", "luis", "Av. Chilacos", "483407", "3202829564", chia, Role.ADMIN));
        userService.registerUser(new User("nicofl", "nicole", "127 #7", "12015215", "3000000000", usaquen, Role.ADMIN));
        userService.registerUser(new User("juanobga", "juanse", "165 #45", "13150241", "3000000000", usaquen, Role.ADMIN));
        userService.registerUser(new User("mateobran", "mateo", "Av. Chilacos", "16852315", "3000000000", chia, Role.ADMIN));
        // REGULAR USERS
        userService.registerUser(new User("edgar04", "pass", "85- #7", "12015298", "3124568520", chapinero, Role.USER));
        userService.registerUser(new User("patricio62", "pass", "100- #73-23", "12065298", "3124568300", suba, Role.USER));
        userService.registerUser(new User("maria12", "pass", "130- #7", "10015298", "3104568520", usaquen, Role.USER));


    }
}