package com.outsider.safetynetalerts.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "medical_record")
public class MedicalRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "medications")
    private Set<String> medications;

    @Column(name = "allergies")
    private Set<String> allergies;

    @OneToOne(optional = false, mappedBy = "medicalRecord")
    @JoinColumn(name = "person_id")
    private PersonEntity person;

}
