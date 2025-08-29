package de.iu.fallstudie.ghostnetfishing.controller;

import de.iu.fallstudie.ghostnetfishing.model.GhostNet;
import de.iu.fallstudie.ghostnetfishing.model.Person;
import de.iu.fallstudie.ghostnetfishing.service.GhostNetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GhostNetController {

    private final GhostNetService ghostNetService;

    // Constructor Injection
    public GhostNetController(GhostNetService ghostNetService) {
        this.ghostNetService = ghostNetService;
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("nets", ghostNetService.findAllUnrecoveredNets());
        return "index";
    }

    @GetMapping("/report")
    public String showReportForm(Model model) {
        model.addAttribute("ghostNet", new GhostNet());
        model.addAttribute("person", new Person());
        return "report-form";
    }

    @GetMapping("/net/{id}")
    public String viewNetDetails(@PathVariable("id") Long id, Model model) {
        GhostNet ghostNet = ghostNetService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ungültige Netz-ID: " + id));

        model.addAttribute("net", ghostNet);
        model.addAttribute("salvager", new Person());
        return "net-details";
    }

    @PostMapping("/report")
    public String reportNet(GhostNet ghostNet, Person person) {
        ghostNetService.reportNet(ghostNet, person);
        return "redirect:/";
    }

    @PostMapping("/net/assign/{id}")
    public String assignSalvager(@PathVariable("id") Long id, Person salvager) {
        ghostNetService.assignSalvager(id, salvager);
        return "redirect:/net/" + id;
    }

    @PostMapping("/net/recover/{id}")
    public String markAsRecovered(@PathVariable("id") Long id) {
        ghostNetService.markAsRecovered(id);
        return "redirect:/net/" + id;
    }

    @PostMapping("/net/lost/{id}")
    public String markAsLost(@PathVariable("id") Long id, Person person) {
        ghostNetService.markAsLost(id, person);
        return "redirect:/net/" + id;
    }
}