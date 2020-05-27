package com.softeng.finalsofteng.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Bus")
public class Bus {

    @Id
    @GeneratedValue
    private long busId;
    @NotNull
    private String placa;
    @NotNull
    private int capacidad;
    @NotNull
    private String marca;
    @ManyToOne
    @JoinColumn(name = "driver_id")
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
