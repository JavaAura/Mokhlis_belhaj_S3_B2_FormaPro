package com.Formation.formationapi.Controleur;

import com.Formation.formationapi.Modele.entity.Formateur;
import com.Formation.formationapi.service.FormateurService;
import com.Formation.formationapi.Exceptions.ResourceNotFoundException;
import com.Formation.formationapi.Exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public ResponseEntity<List<Formateur>> getAllFormateurs() {
        List<Formateur> formateurs = formateurService.getAllFormateurs();
        return ResponseEntity.status(HttpStatus.OK).body(formateurs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Formateur> getFormateurById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("Invalid formateur ID: " + id);
        }
        Optional<Formateur> formateur = formateurService.getFormateurById(id);
        if (formateur.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(formateur.get());
        } else {
            throw new ResourceNotFoundException("Formateur not found with id: " + id);

        }
    }

    @PostMapping
    public ResponseEntity<Formateur> createFormateur(@Valid @RequestBody Formateur formateur) {
        if (formateur.getNom() == null || formateur.getNom().trim().isEmpty()) {
            throw new ValidationException("Le nom du formateur est obligatoire");
        }
        if (formateur.getEmail() == null || formateur.getEmail().trim().isEmpty()) {
            throw new ValidationException("L'email du formateur est obligatoire");
        }
        
        Formateur newFormateur = formateurService.createFormateur(formateur);
        return ResponseEntity.status(HttpStatus.CREATED).body(newFormateur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Formateur> updateFormateur(@PathVariable Long id, 
                                                   @Valid @RequestBody Formateur formateur) {
        Formateur existingFormateur = formateurService.getFormateurById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Formateur not found with id: " + id));
        
        if (formateur.getNom() != null) {
            existingFormateur.setNom(formateur.getNom());
        }
        if (formateur.getEmail() != null) {
            existingFormateur.setEmail(formateur.getEmail());
        }
        if (formateur.getSpecialite() != null) {
            existingFormateur.setSpecialite(formateur.getSpecialite());
        }
        
        return ResponseEntity.ok(formateurService.updateFormateur(existingFormateur));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormateur(@PathVariable Long id) {
        if (!formateurService.getFormateurById(id).isPresent()) {
            throw new ResourceNotFoundException("Formateur not found with id: " + id);
        }
        formateurService.deleteFormateur(id);
        return ResponseEntity.noContent().build();
    }
}
