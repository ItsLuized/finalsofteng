package com.softeng.finalsofteng.model;

import com.softeng.finalsofteng.model.composite.Componente;

public class User implements Componente {
    private String email;
    private String password;
    private String documento;
    private String direccion;
    private String telefono;
    private String ciudad = "";

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.documento = "";
        this.direccion = "";
        this.telefono = "";
        this.ciudad = "";
    }

    public User(String email, String password, String direccion, String documento, String telefono) {
        this.email = email;
        this.password = password;
        this.documento = documento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.ciudad = "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento  = documento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Email: " + email + ",Password: " + password + ",Documento: " + documento + ",Direccion: " + direccion + ",Telefono: " + telefono;
    }

    @Override
    public String lugar(String ciudad) {
        this.ciudad = ciudad;
        return this.email;
    }
}
