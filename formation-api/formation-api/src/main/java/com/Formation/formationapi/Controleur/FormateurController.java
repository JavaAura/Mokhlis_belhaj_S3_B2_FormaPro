package com.Formation.formationapi.Controleur;

import com.Formation.formationapi.Modele.entity.Formateur;
import com.Formation.formationapi.service.FormateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/formateurs")
@CrossOrigin(origins = "*")
public class FormateurController {

    private final FormateurService formateurService;

    @Autowired
    public FormateurController(FormateurService formateurService) {
        this.formateurService = formateurService;
    }

    // Get all formateurs
    @GetMapping
    public List<Formateur> getAllFormateurs() {
        return formateurService.getAllFormateurs();
    }

    // Get formateur by ID
    @GetMapping("/{id}")
    public ResponseEntity<Formateur> getFormateurById(@PathVariable Long id) {
        return formateurService.getFormateurById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create new formateur
    @PostMapping
    public ResponseEntity<Formateur> createFormateur(@RequestBody Formateur formateur) {
        Formateur newFormateur = formateurService.createFormateur(formateur);
        return ResponseEntity.created(null).body(newFormateur);
    }

    // Update formateur
    @PutMapping("/{id}")
    public ResponseEntity<Formateur> updateFormateur(@PathVariable Long id, 
                                                   @Valid @RequestBody Formateur formateur) {
        return formateurService.getFormateurById(id)
                .map(existing -> {
                    formateur.setId(id);
                    return ResponseEntity.ok(formateurService.updateFormateur(formateur));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete formateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormateur(@PathVariable Long id) {
        Optional<Formateur> formateur = formateurService.getFormateurById(id);
        if (formateur.isPresent()) {
            formateurService.deleteFormateur(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
