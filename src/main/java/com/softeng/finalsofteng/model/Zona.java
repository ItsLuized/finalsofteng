package com.softeng.finalsofteng.model;

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
    @OneToMany
    private List<Zona> zonasHijas;
    @OneToOne
    private Zona zonaPadre;

    public Zona(String nombreLugar) {
        this.nombreLugar = nombreLugar;
        this.zonasHijas = new ArrayList<>();
    }

    public void add(Zona zona) {
        this.zonasHijas.add(zona);
    }

    public List<Zona> getLista() {
        return zonasHijas;
    }

    public void setLista(List<Zona> zona) {
        this.zonasHijas = zona;
    }

}
