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
@Table(name = "packages")
public class Packaging extends BaseEntity {

    private final String EXPIRATION_DATE_PATTERN = "yyyy-MM-dd";

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @OneToMany(mappedBy = "packaging")
    private List<Item> items;

    @ManyToOne(cascade = MERGE)
    @JoinColumn(name = "box_id")
    private Box box;

    @Column(name = "expiration_date")
    @JsonFormat(shape = STRING, pattern = EXPIRATION_DATE_PATTERN)
    private LocalDate expirationDate;

    @Column(name = "description")
    private String description;
}
