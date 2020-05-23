package com.softeng.finalsofteng.model.decorator;

public class BusRuta extends BusInfo {

    private String ruta;

    public BusRuta(Bus busInfo, String ruta) {
        super(busInfo);
        this.ruta = ruta;
    }

    @Override
    public String getTodo() {
        return busInfo.getTodo() + addRuta(this.ruta);
    }

    @Override
    public void setTodo(String placa, int capacidad, String marca) {
        busInfo.setTodo(placa, capacidad, marca);
    }

    public String addRuta(String ruta){
        return ",Ruta: " + ruta;
    }
}
