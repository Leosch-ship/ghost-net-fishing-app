package de.iu.fallstudie.ghostnetfishing.repository;

import de.iu.fallstudie.ghostnetfishing.model.GhostNet;
import de.iu.fallstudie.ghostnetfishing.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GhostNetRepository extends JpaRepository<GhostNet, Long> {

    /**
     * Findet alle Netze, deren Status in der übergebenen Liste enthalten ist.
     * Wird genutzt, um z.B. alle "aktiven" (GEMELDET, BERGUNG_BEVORSTEHEND) Netze zu laden.
     */
    List<GhostNet> findByStatusIn(List<Status> statuses);
}