package com.moonbox.inventory.entities;

import jakarta.persistence.*;
import lombok.Data;

import static jakarta.persistence.GenerationType.UUID;

@Data
@Entity
@Table(name = "measuring_units")
public class MeasuringUnit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "symbol", unique = true)
    private String symbol;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "description")
    private String description;
}
