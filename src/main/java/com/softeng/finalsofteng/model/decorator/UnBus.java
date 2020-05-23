package com.softeng.finalsofteng.model.decorator;

public class UnBus implements Bus {

    private String placa;
    private int capacidad;
    private String marca;

    public UnBus(String placa, int capacidad, String marca) {
        this.placa = placa;
        this.capacidad = capacidad;
        this.marca = marca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @Override
    public String getTodo() {
        return "Placa: " + placa + ",Capacidad: " + capacidad + ",Marca: " + marca;
    }

    @Override
    public void setTodo(String placa, int capacidad, String marca) {
        this.placa = placa;
        this.capacidad = capacidad;
        this.marca = marca;
    }
}
