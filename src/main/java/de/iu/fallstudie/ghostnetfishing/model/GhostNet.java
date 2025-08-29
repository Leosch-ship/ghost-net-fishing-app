package de.iu.fallstudie.ghostnetfishing.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// Repräsentiert einen Geisternetz-Eintrag in der Datenbank.
@Entity
@Table(name = "ghost_nets")
@Getter
@Setter
public class GhostNet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Standort & Größe
    private double latitude;
    private double longitude;
    private String estimatedSize;

    @Enumerated(EnumType.STRING)
    private Status status;

    // Beziehungen zu Personen
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "reporting_person_id")
    private Person reportingPerson;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "salvaging_person_id")
    private Person salvagingPerson;
}