package com.softeng.finalsofteng.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Zona")
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
