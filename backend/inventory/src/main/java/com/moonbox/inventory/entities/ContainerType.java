package com.moonbox.inventory.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import static jakarta.persistence.GenerationType.UUID;

/***
 * Represents the type of physical container of a single indivisible item.
 * For example: a tin can of tuna
 */

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

    @ManyToOne
    @JoinColumn(name = "uom_id")
    private MeasuringUnit measuringUnit;

    @Column(name = "amount")
    private double amount;
}
