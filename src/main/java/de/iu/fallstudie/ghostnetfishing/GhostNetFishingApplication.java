// Unser Haupt-Package für das ganze Projekt
package de.iu.fallstudie.ghostnetfishing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hauptklasse und Startpunkt für die GhostNetFishing-Anwendung.
 * Die @SpringBootApplication-Annotation kümmert sich um die meiste Magie,
 * wie die Autokonfiguration und das Scannen nach unseren Komponenten.
 */
@SpringBootApplication
public class GhostNetFishingApplication {

    /**
     * Die gute alte main-Methode. Startet die komplette Spring-Anwendung.
     *
     * @param args Kommandozeilen-Argumente (werden aktuell aber nicht genutzt).
     */
    public static void main(String[] args) {
        // Hier wird die App gestartet... und los!
        SpringApplication.run(GhostNetFishingApplication.class, args);
    }
}