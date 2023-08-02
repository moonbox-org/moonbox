package com.moonbox.inventory.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.GenerationType.UUID;

@Data
@Entity
@Table(name = "packagings")
public class Packaging extends BaseEntity {

    private static final String EXPIRATION_DATE_PATTERN = "yyyy-MM-dd";

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "packaging_type_id")
    private PackagingType packagingType;

    @OneToMany(mappedBy = "packaging")
    private List<Item> items;

    @ManyToOne(cascade = MERGE)
    @JoinColumn(name = "box_id")
    private Box box;

    @ManyToOne
    @JoinColumn(name = "lot_id")
    private Lot lot;

    @Column(name = "expiration_date")
    @JsonFormat(shape = STRING, pattern = EXPIRATION_DATE_PATTERN)
    private LocalDate expirationDate;

    @Column(name = "description")
    private String description;
}
