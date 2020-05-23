package com.softeng.finalsofteng.model.decorator;

abstract class BusInfo implements Bus {

    protected Bus busInfo;

    public BusInfo(Bus busInfo) {
        this.busInfo = busInfo;
    }


    @Override
    public String getTodo() {
        return busInfo.getTodo();
    }

    @Override
    public void setTodo(String placa, int capacidad, String marca) {
        busInfo.setTodo(placa, capacidad, marca);
    }
}
