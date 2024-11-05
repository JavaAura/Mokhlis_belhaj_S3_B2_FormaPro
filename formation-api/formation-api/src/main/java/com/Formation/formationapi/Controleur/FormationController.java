package com.Formation.formationapi.Controleur;

import com.Formation.formationapi.Modele.entity.Formation;
import com.Formation.formationapi.service.FormationService;
import com.Formation.formationapi.Exceptions.ResourceNotFoundException;
import com.Formation.formationapi.Exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.List;
import java.lang.reflect.Field;

@RestController
@RequestMapping("/api/formations")
public class FormationController {

    @Autowired
    private FormationService formationService;

    // Create
    @PostMapping
    public ResponseEntity<Formation> createFormation(@Valid @RequestBody Formation formation) {
        if (formation.getTitre() == null || formation.getTitre().trim().isEmpty()) {
            throw new ValidationException("Le titre de la formation est obligatoire");
        }
        Formation newFormation = formationService.saveFormation(formation);
        return ResponseEntity.ok(newFormation);
    }

    // Read all
    @GetMapping
    public ResponseEntity<Page<Formation>> getAllFormations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Formation> formationPage = formationService.getAllFormations(pageable);
        return ResponseEntity.ok(formationPage);
    }

    // Read one
    @GetMapping("/{id}")
    public ResponseEntity<Formation> getFormationById(@PathVariable Long id) {
        Formation formation = formationService.getFormationById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Formation not found with id: " + id));
        return ResponseEntity.ok(formation);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Formation> updateFormation(@PathVariable Long id, @Valid @RequestBody Formation formation) {
        Formation existingFormation = formationService.getFormationById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Formation not found with id: " + id));
        
        try {
            for (Field field : formation.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(formation);
                if (value != null) {
                    if (field.getType().equals(int.class)) {
                        int intValue = (int) value;
                        if (intValue >= 1) {
                            field.set(existingFormation, value);
                        } else {
                            throw new ValidationException("Invalid value for field " + field.getName() + ": must be >= 1");
                        }
                    } else if (!value.toString().isEmpty()) {
                        field.set(existingFormation, value);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new ValidationException("Error updating formation: " + e.getMessage());
        }
        
        existingFormation.setId(id);
        Formation updatedFormation = formationService.saveFormation(existingFormation);
        return ResponseEntity.ok(updatedFormation);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) {
        if (!formationService.getFormationById(id).isPresent()) {
            throw new ResourceNotFoundException("Formation not found with id: " + id);
        }
        formationService.deleteFormation(id);
        return ResponseEntity.noContent().build();
    }
}
