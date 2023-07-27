package com.moonbox.inventory.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.UUID;

@Data
@Entity
@Table(name = "items")
public class Item extends BaseEntity {

    private static final String EXPIRATION_DATE_PATTERN = "yyyy-MM-dd";

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

    @ManyToOne
    @JoinColumn(name = "container_type_id")
    private ContainerType containerType;

    @Column(name = "expiration_date")
    @JsonFormat(shape = STRING, pattern = EXPIRATION_DATE_PATTERN)
    private LocalDate expirationDate;

    @Column(name = "description")
    private String description;
}
