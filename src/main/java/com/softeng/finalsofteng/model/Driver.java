package com.softeng.finalsofteng.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "conductor")
public class Driver {

    @Id
    @GeneratedValue
    private long driverId;

    private String name;
    private String lastName;
    private int Age;
}
