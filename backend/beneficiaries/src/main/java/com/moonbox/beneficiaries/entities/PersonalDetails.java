package com.moonbox.beneficiaries.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static jakarta.persistence.GenerationType.AUTO;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Data
@Entity
@Table(name = "personal_details")
@EntityListeners(AuditingEntityListener.class)
public class PersonalDetails extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "fathers_name")
    private String fathersName;

    @JsonFormat(shape = STRING)
    @DateTimeFormat(iso = DATE)
    private LocalDate dateOfBirth;

    @JsonFormat(shape = STRING)
    @DateTimeFormat(iso = DATE)
    private LocalDate dateOfArrivalInGreece;

    @JsonFormat(shape = STRING)
    @DateTimeFormat(iso = DATE)
    private LocalDate dateOfArrivalInAthens;
}
