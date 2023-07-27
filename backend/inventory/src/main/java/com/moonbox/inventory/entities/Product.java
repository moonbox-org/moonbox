package com.moonbox.inventory.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.Set;

import static jakarta.persistence.GenerationType.UUID;

@Data
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "active", nullable = false)
    private boolean active;

    @ManyToMany
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "products_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "categories_id", referencedColumnName = "id"))
    private List<Category> categories;

    @ManyToMany
    @JoinTable(
            name = "products_tags",
            joinColumns = @JoinColumn(name = "products_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id", referencedColumnName = "id"))
    private Set<ProductTag> tags;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "vendor")
    private String vendor;

    @Column(name = "average_unit_price")
    private double averageUnitPrice;
}
