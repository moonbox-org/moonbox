package com.moonbox.beneficiaries.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.AUTO;

@Data
@Entity
@Table(name = "beneficiary_practices")
@EntityListeners(AuditingEntityListener.class)
public class BeneficiaryPractice extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @JsonBackReference
    @OneToOne(mappedBy = "practice", fetch = EAGER)
    private Beneficiary beneficiary;

}
