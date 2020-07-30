package com.softeng.finalsofteng.controller;

import com.softeng.finalsofteng.model.Bus;
import com.softeng.finalsofteng.model.Driver;
import com.softeng.finalsofteng.model.Route;
import com.softeng.finalsofteng.repository.IBusRepository;
import com.softeng.finalsofteng.repository.IDriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class BusController {

    private final IBusRepository busRepository;
    private final IDriverRepository driverRepository;

    @Autowired
    public BusController(IBusRepository busRepository, IDriverRepository driverRepository) {
        this.busRepository = busRepository;
        this.driverRepository = driverRepository;
    }

    @GetMapping("/menu-bus")
    public String menuBus() {
        return "MenuBus";
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
        busRepository.save(nuevoBus);
        return "redirect:/menu-bus";
    }

    @GetMapping("/listabus")
    public String listarBuses(Model model) {
        List<Bus> listaDeBuses = busRepository.findAll();
        model.addAttribute("listaDeBuses", listaDeBuses);
        return "ListaBuses";
    }
}
