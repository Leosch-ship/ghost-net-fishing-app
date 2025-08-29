package de.iu.fallstudie.ghostnetfishing.controller;

import de.iu.fallstudie.ghostnetfishing.model.GhostNet;
import de.iu.fallstudie.ghostnetfishing.model.Person;
import de.iu.fallstudie.ghostnetfishing.service.GhostNetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class GhostNetController {

    private final GhostNetService ghostNetService;

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

    @PostMapping("/report")
    public String reportNet(GhostNet ghostNet, Person person) {
        ghostNetService.reportNet(ghostNet, person);
        return "redirect:/";
    }

    @GetMapping("/net/{id}")
    public String viewNetDetails(@PathVariable("id") Long id, Model model) {
        GhostNet ghostNet = ghostNetService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid net Id:" + id));
        model.addAttribute("net", ghostNet);
        model.addAttribute("salvager", new Person());
        return "net-details";
    }
    @PostMapping("/net/assign/{id}")
    public String assignSalvager(@PathVariable("id") Long id, Person salvager) {
        ghostNetService.assignSalvager(id, salvager);
        return "redirect:/net/" + id; // KORRIGIERT
    }

    @PostMapping("/net/recover/{id}")
    public String markAsRecovered(@PathVariable("id") Long id) {
        ghostNetService.markAsRecovered(id);
        return "redirect:/net/" + id; // KORRIGIERT
    }
}