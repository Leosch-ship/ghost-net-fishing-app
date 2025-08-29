package de.iu.fallstudie.ghostnetfishing.service;

import de.iu.fallstudie.ghostnetfishing.model.GhostNet;
import de.iu.fallstudie.ghostnetfishing.model.Person;
import de.iu.fallstudie.ghostnetfishing.model.Status;
import de.iu.fallstudie.ghostnetfishing.repository.GhostNetRepository;
import de.iu.fallstudie.ghostnetfishing.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GhostNetService {

    private final GhostNetRepository ghostNetRepository;
    private final PersonRepository personRepository;

    /**
     * Holt alle Netze, die noch nicht geborgen wurden (Status GEMELDET oder BERGUNG_BEVORSTEHEND).
     */
    public List<GhostNet> findAllUnrecoveredNets() {
        return ghostNetRepository.findByStatusIn(List.of(Status.GEMELDET, Status.BERGUNG_BEVORSTEHEND));
    }

    public Optional<GhostNet> findById(Long id) {
        return ghostNetRepository.findById(id);
    }

    /**
     * Speichert eine neue Netz-Meldung.
     * Eine meldende Person wird nur angehängt, wenn auch ein Name angegeben wurde.
     */
    @Transactional
    public void reportNet(GhostNet net, Person reporter) {
        if (reporter != null && reporter.getName() != null && !reporter.getName().isBlank()) {
            net.setReportingPerson(reporter);
        }
        net.setStatus(Status.GEMELDET);
        ghostNetRepository.save(net);
    }

    /**
     * Weist einem Netz eine Person zur Bergung zu.
     * Falls die Person (Name + Telefon) noch nicht existiert, wird sie neu angelegt.
     */
    @Transactional
    public void assignSalvager(Long netId, Person salvagerFromForm) {
        // Prüfen, ob die Person schon existiert, sonst neu anlegen.
        Person managedSalvager = personRepository
                .findByNameAndPhone(salvagerFromForm.getName(), salvagerFromForm.getPhone())
                .orElseGet(() -> personRepository.save(salvagerFromForm));

        // Das zugehörige Netz laden.
        GhostNet net = ghostNetRepository.findById(netId)
                .orElseThrow(() -> new IllegalStateException("Netz mit ID " + netId + " nicht gefunden."));

        // Person und Status aktualisieren und speichern.
        net.setSalvagingPerson(managedSalvager);
        net.setStatus(Status.BERGUNG_BEVORSTEHEND);
        ghostNetRepository.save(net);
    }

    /**
     * Ändert den Status eines Netzes auf GEBORGEN.
     */
    @Transactional
    public void markAsRecovered(Long netId) {
        GhostNet net = ghostNetRepository.findById(netId)
                .orElseThrow(() -> new IllegalStateException("Netz mit ID " + netId + " nicht gefunden."));

        net.setStatus(Status.GEBORGEN);
        ghostNetRepository.save(net);
    }
}