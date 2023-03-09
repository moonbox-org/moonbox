package com.moonbox.beneficiaries.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.AUTO;

@Data
@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "native_name")
    private String nativeName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "capital")
    private String capital;

    @Column(name = "currency")
    private String currency;

    @ManyToOne(cascade = MERGE)
    @JoinColumn(name = "continent_id")
    private Continent continent;

    @ManyToMany(cascade = PERSIST)
    @JoinTable(name = "countries_languages",
            joinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "languages_id", referencedColumnName = "id")
    )
    private Set<Language> languages;
}
