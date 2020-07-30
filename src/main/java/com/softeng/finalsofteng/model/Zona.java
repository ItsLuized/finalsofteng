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
@Table(name = "Zona")
public class Zona {

    @Id
    @GeneratedValue
    private long zonaId;
    @NotNull
    private String nombreLugar;
    @OneToOne
    private Zona zonaPadre;

    public Zona(String nombreLugar) {
        this.nombreLugar = nombreLugar;
    }
}
