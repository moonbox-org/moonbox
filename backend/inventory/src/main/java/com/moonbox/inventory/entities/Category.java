package com.moonbox.inventory.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import static jakarta.persistence.GenerationType.UUID;

@Data
@Entity
@Table(name = "categories")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Category {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @Column(nullable = false, name = "name")
    private String name;

    @JsonInclude
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    private List<Category> children;

    @JsonInclude
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Category parent;

}
