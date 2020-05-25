package com.softeng.finalsofteng.model.decorator;

abstract class BusInfo implements IBus {

    protected IBus busInfo;

    public BusInfo(IBus busInfo) {
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
