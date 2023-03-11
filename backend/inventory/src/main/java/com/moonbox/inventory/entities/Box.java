package com.moonbox.inventory.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import static jakarta.persistence.GenerationType.UUID;

@Data
@Entity
@Table(name = "boxes")
public class Box extends BaseEntity {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @OneToMany(mappedBy = "box")
    private List<Packaging> packagingList;
}
