package com.softeng.finalsofteng.model.composite;

import java.util.ArrayList;
import java.util.List;

public class Zona implements Componente {
    private String ciudad;
    private List<Componente> lista;

    public Zona() {
        this.ciudad = "";
        this.lista = new ArrayList<>();
    }

    public Zona(String ciudad) {
        this.ciudad = ciudad;
        this.lista = new ArrayList<>();
    }

    public void add(Componente componente) {
        this.lista.add(componente);
    }

    public List<Componente> getLista() {
        return lista;
    }

    public void setLista(List<Componente> lista) {
        this.lista = lista;
    }

    @Override
    public String lugar(String ciudad) {
        StringBuilder stringBuilder = new StringBuilder();
        int contador = 0;
        this.ciudad = ciudad;
        for (Componente componente : this.lista) {
            componente.lugar(ciudad);
            if (contador < 1) {
                stringBuilder.append(componente.lugar(ciudad));
                contador++;
            } else {
                stringBuilder.append(componente.lugar(ciudad)).append("\n");
            }
        }
        //return "Ellos viven en la ciudad " + this.ciudad + ":\n" + stringBuilder.toString();
        return this.ciudad + "," + stringBuilder.toString();
    }

}
