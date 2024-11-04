package com.Formation.formationapi.Controleur;

import com.Formation.formationapi.Modele.entity.Formation;
import com.Formation.formationapi.service.FormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.lang.reflect.Field;

@RestController
@RequestMapping("/api/formations")
public class FormationController {

    @Autowired
    private FormationService formationService;

    // Create
    @PostMapping
    public ResponseEntity<Formation> createFormation(@RequestBody Formation formation) {
        Formation newFormation = formationService.saveFormation(formation);
        return ResponseEntity.ok(newFormation);
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<Formation>> getAllFormations() {
        List<Formation> formations = formationService.getAllFormations();
        return ResponseEntity.ok(formations);
    }

    // Read one
    @GetMapping("/{id}")
    public ResponseEntity<Formation> getFormationById(@PathVariable Long id) {
        Optional<Formation> formation = formationService.getFormationById(id);
        return formation.isPresent() ? ResponseEntity.ok(formation.get()) : ResponseEntity.notFound().build();
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Formation> updateFormation(@PathVariable Long id, @RequestBody Formation formation) {
        Optional<Formation> existingFormationOpt = formationService.getFormationById(id);
        if (!existingFormationOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        Formation existingFormation = existingFormationOpt.get();
        
        try {
            for (Field field : formation.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(formation);
                if (value != null) {
                    if (field.getType().equals(int.class)) {
                        int intValue = (int) value;
                        if (intValue >= 1) {
                            field.set(existingFormation, value);
                        }
                    } else if (!value.toString().isEmpty()) {
                        field.set(existingFormation, value);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().build();
        }
        
        existingFormation.setId(id);
        Formation updatedFormation = formationService.saveFormation(existingFormation);
        return ResponseEntity.ok(updatedFormation);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) {
        Optional<Formation> formation = formationService.getFormationById(id);
        if (formation.isPresent()) {
            formationService.deleteFormation(id);
            return ResponseEntity.noContent().build();
        }
        formationService.deleteFormation(id);
        return ResponseEntity.noContent().build();
    }
}
