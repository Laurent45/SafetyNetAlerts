package com.outsider.safetynetalerts.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String birthdate;

    @ElementCollection
    private List<String> medications;
    @ElementCollection
    private List<String> allergies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MedicalRecord that = (MedicalRecord) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 1503583049;
    }
}
