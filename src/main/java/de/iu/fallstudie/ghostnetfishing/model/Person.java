package de.iu.fallstudie.ghostnetfishing.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// Repräsentiert eine Person (Melder oder Berger).
@Entity
@Table(name = "persons")
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
}