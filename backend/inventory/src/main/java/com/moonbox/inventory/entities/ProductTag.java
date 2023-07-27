package com.moonbox.inventory.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import static jakarta.persistence.GenerationType.UUID;

@Data
@Entity
@Table(name = "product_tags")
public class ProductTag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @NotBlank
    @Column(name = "value", nullable = false)
    private String value;
}
