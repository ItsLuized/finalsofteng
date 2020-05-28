package com.softeng.finalsofteng.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    /*
    @JsonIdentityReference(alwaysAsId=true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    */
    private Zona zona;

    private Role role;

    public User(String email, String password, String direccion, String documento, String telefono, Zona zona, Role role) {
        this.email = email;
        this.password = password;
        this.direccion = direccion;
        this.documento = documento;
        this.telefono = telefono;
        this.zona = zona;
        this.role = role;
    }

    public User(String email, String password, String direccion, String documento, String telefono) {
        this.email = email;
        this.password = password;
        this.direccion = direccion;
        this.documento = documento;
        this.telefono = telefono;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Email: " + email + ",Password: " + password + ",Documento: " + documento + ",Direccion: " + direccion + ",Telefono: " + telefono;
    }

}
