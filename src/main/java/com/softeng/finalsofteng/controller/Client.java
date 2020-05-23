package com.softeng.finalsofteng.controller;


import java.math.BigInteger;

public class Client {
    private Proxy proxy;
    private Facade facade;
    private Encryption encryption;

    public Client() {
        this.proxy = Proxy.getInstance();
        this.facade = Facade.getInstance();
    }

    public static void main(String[] args) {
        new Client();
    }

    public String registerUser(String email, String password) {
        this.proxy.registerUser(email, password);
        return "Usuario registrado";
    }

    public BigInteger accederSistema(String email, String password, String IP) throws Exception {
        BigInteger nonce = this.proxy.accederSistema(email, password, IP);
        this.encryption = Encryption.getInstance(nonce.toString());
        return nonce;
    }

    public Object elaborarOperacion(String mesgNotEncrypted, String ip) throws Exception {
        return this.facade.elaborarOperacion(this.encryption.encrypt(mesgNotEncrypted), ip);
    }

    public String getZonas() {
        return this.proxy.getZonasString();
    }

    public boolean existeZona(String zona) {
        return this.proxy.existeZona(zona);
    }

}
