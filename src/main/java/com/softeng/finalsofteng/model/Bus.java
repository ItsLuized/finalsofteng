package com.softeng.finalsofteng.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table
public class Bus {

    @Id
    @GeneratedValue
    private long idBus;
    @NotNull
    private String placa;
    @NotNull
    private int capacidad;
    @NotNull
    private String marca;
    @ManyToOne
    private Driver driver;
    @Enumerated(EnumType.STRING)
    private Route route;

    public Bus(String placa, int capacidad, String marca, Driver driver, Route route){
        this.placa = placa;
        this.capacidad = capacidad;
        this.marca = marca;
        this.driver = driver;
        this.route = route;
    }

}
