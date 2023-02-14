package com.moonbox.beneficiaries.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.AUTO;

@Data
@Entity
@Table(name = "personal_details")
@EntityListeners(AuditingEntityListener.class)
public class PersonalDetails extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;
}
