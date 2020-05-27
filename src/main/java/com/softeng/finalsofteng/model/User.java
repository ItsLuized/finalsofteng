package com.softeng.finalsofteng.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Usuario", uniqueConstraints = { @UniqueConstraint(columnNames = { "email", "documento", "telefono" }) })
public class User {

    @Id
    @GeneratedValue
    private long userId;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String documento;

    private String direccion;
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "zona_id")
    private Zona zona;

    public User(String email, String password, String direccion, String documento, String telefono, Zona zona) {
        this.email = email;
        this.password = password;
        this.direccion = direccion;
        this.documento = documento;
        this.telefono = telefono;
        this.zona = zona;
    }

    public User(String email, String password, String direccion, String documento, String telefono) {
        this.email = email;
        this.password = password;
        this.direccion = direccion;
        this.documento = documento;
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Email: " + email + ",Password: " + password + ",Documento: " + documento + ",Direccion: " + direccion + ",Telefono: " + telefono;
    }

}
