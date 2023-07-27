package com.moonbox.inventory.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import static jakarta.persistence.GenerationType.UUID;

@Data
@Entity
@Table(name = "container_types")
public class ContainerType {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;


    private double amount;
}
