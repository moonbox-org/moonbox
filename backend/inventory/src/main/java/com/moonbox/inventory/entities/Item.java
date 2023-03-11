package com.moonbox.inventory.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.UUID;

@Data
@Entity
@Table(name = "items")
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(cascade = PERSIST, fetch = EAGER)
    @JoinTable(name = "items_products",
            joinColumns = @JoinColumn(name = "items_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "products_id", referencedColumnName = "id"))
    private Set<Product> product;

    @ManyToOne(cascade = MERGE)
    @JoinColumn(name = "package_id")
    private Packaging packaging;
}
