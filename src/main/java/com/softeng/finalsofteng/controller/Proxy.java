package com.softeng.finalsofteng.controller;

import com.softeng.finalsofteng.model.SystemRemoteException;
import com.softeng.finalsofteng.model.User;
import com.softeng.finalsofteng.model.composite.Zona;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class Proxy {
    private static Proxy instance = null;
    private final Map<String, User> users;
    private final Map<String, BigInteger> ipNonce;
    private final Facade facade;
    private final Map<String, Zona> zonas;
    private BigInteger nonce;

    private Proxy() {
        this.users = new HashMap();
        this.ipNonce = new HashMap<>();
        facade = Facade.getInstance();
        this.zonas = new HashMap<>();
        this.nonce = new BigInteger("0");
    }

    public static Proxy getInstance() {
        if (instance == null) {
            instance = new Proxy();
        }
        return instance;
    }

    public void registerUser(String email, String password) {
        if (!this.users.containsKey(email))
            this.users.put(email, new User(email, password));
    }

    /*public void registerUser(String email, String password, String nombre, String direccion, String telefono, String ciudad) {
        if (!this.users.containsKey(email))
            this.users.put(email, new User(email, password, nombre, direccion, telefono));
        if (!this.zonas.containsKey(ciudad)) {
            this.zonas.put(ciudad, new Zona(ciudad));
        }
        this.zonas.get(ciudad).add(this.users.get(email));
    }*/

    public BigInteger accederSistema(String email, String password, String ip) throws Exception {
        User user = new User(email, password);
        if (!this.users.containsKey(user.getEmail())) throw new SystemRemoteException("User not registered");
        User userDB = this.users.get(user.getEmail());
        if (!userDB.getPassword().equals(user.getPassword())) throw new SystemRemoteException("Invalid credentials");

        this.nonce = BigInteger.probablePrime(2048, new SecureRandom());
        this.facade.registrarSesion(ip, user);
        this.ipNonce.put(ip, nonce);
        return nonce;
    }

    public Map<String, Zona> getZonas() {
        return zonas;
    }

    public String getZonasString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Zona> entry : this.getZonas().entrySet()) {
            stringBuilder.append(entry.getKey() + "\n");
        }
        return stringBuilder.toString();
    }

    public boolean existeZona(String zona) {
        boolean existe = false;
        if (this.zonas.containsKey(zona)) {
            existe = true;
        }
        return existe;
    }

    public boolean crearContenedor(String ciudad){
        Zona zona = new Zona();
        zona.lugar(ciudad);
        this.zonas.put(ciudad, zona);
        return this.zonas.containsKey(ciudad);
    }

    public BigInteger getNonce() {
        return nonce;
    }

    public BigInteger getNonceByIP(String ip){
        return this.ipNonce.get(ip);
    }

    public String agregarUsuarioAZona(String ciudad, String documento){
        String emailUsuario = "";
        String retorno = "fallo";
        for(User user : this.users.values()){
            if (user.getDocumento().equals(documento))
                emailUsuario = user.getEmail();
        }
        if (!emailUsuario.equals("")){
            retorno = "void";
        }
        this.zonas.get(ciudad).add(this.users.get(emailUsuario));
        return retorno;
    }

    public void remplazarUsuario(User oldUser, User newUser){
        this.users.remove(oldUser.getEmail());
        this.users.put(newUser.getEmail(), newUser);
    }

    public Map<String, User> getUsers() {
        return users;
    }
}
