package com.moonbox.inventory.entities;

import jakarta.persistence.*;
import lombok.Data;

import static jakarta.persistence.GenerationType.UUID;

@Data
@Entity
@Table(name = "packaging_types")
public class PackagingType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
