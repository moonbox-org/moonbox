package com.moonbox.inventory.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static jakarta.persistence.GenerationType.UUID;

@Data
@Entity
@Table(name = "lots")
public class Lot extends BaseEntity {

    private static final String EXPIRATION_DATE_PATTERN = "yyyy-MM-dd";

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @Column(name = "lot_number", nullable = false, unique = true)
    private String lotNumber;

    @Column(name = "expiration_date")
    @JsonFormat(shape = STRING, pattern = EXPIRATION_DATE_PATTERN)
    private LocalDate expirationDate;
}
