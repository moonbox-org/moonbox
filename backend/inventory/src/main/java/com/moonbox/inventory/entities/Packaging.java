package com.moonbox.inventory.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.GenerationType.UUID;

@Data
@Entity
@Table(name = "packages")
public class Packaging extends BaseEntity {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @OneToMany(mappedBy = "packaging")
    private List<Item> items;

    @ManyToOne(cascade = MERGE)
    @JoinColumn(name = "box_id")
    private Box box;
}
