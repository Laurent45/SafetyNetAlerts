package com.outsider.safetynetalerts.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "fire_station")
public class FireStationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "station", nullable = false)
    private int station;

    @ManyToMany
    @JoinTable(
            name = "fire_station_person",
            joinColumns = @JoinColumn(name = "fire_station_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<PersonEntity> persons;
}
