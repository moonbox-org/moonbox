package com.moonbox.beneficiaries.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.AUTO;

@Data
@Entity
@Table(name = "beneficiaries")
@EntityListeners(AuditingEntityListener.class)
public class Beneficiary extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @NotNull
    @Column(name = "beneficiary_id", unique = true, nullable = false)
    private String beneficiaryId;

    @JsonManagedReference
    @OneToOne(cascade = PERSIST, fetch = EAGER)
    @JoinColumn(name = "practice_id", referencedColumnName = "id")
    private BeneficiaryPractice practice;

    @JsonManagedReference
    @OneToOne(cascade = ALL, fetch = LAZY)
    @JoinColumn(name = "personal_details_id", referencedColumnName = "id")
    private PersonalDetails personalDetails;

}
