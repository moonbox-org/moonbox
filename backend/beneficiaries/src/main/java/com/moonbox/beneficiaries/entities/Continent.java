package com.moonbox.beneficiaries.entities;

import jakarta.persistence.*;
import lombok.Data;

import static jakarta.persistence.GenerationType.AUTO;

@Data
@Entity
@Table(name = "continents")
public class Continent {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;
}
