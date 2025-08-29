package de.iu.fallstudie.ghostnetfishing.repository;

import de.iu.fallstudie.ghostnetfishing.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    /**
     * Sucht eine Person anhand von Name und Telefonnummer, um Duplikate zu vermeiden.
     */
    Optional<Person> findByNameAndPhone(String name, String phone);
}