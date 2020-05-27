package com.softeng.finalsofteng.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "conductor")
public class Driver {

    @Id
    @GeneratedValue
    private long driverId;

    private String name;
    private String lastName;
    private int age;

    public Driver(String name, String lastName, int age) {
        this.name= name;
        this.lastName = lastName;
        this.age = age;
    }

}
