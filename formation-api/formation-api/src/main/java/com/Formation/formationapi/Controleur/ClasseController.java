package com.Formation.formationapi.Controleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Formation.formationapi.service.ClasseService;
import com.Formation.formationapi.Modele.entity.Classe;
import com.Formation.formationapi.Exceptions.ResourceNotFoundException;
import com.Formation.formationapi.Exceptions.ValidationException;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("api/classes")
public class ClasseController {

    private final ClasseService classeService;

    @Autowired
    public ClasseController(ClasseService classeService) {
        this.classeService = classeService;
    }

    @GetMapping 
    public ResponseEntity<Page<Classe>> getAllClasses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Classe> classePage = classeService.getAllClasses(pageable);
        return ResponseEntity.ok(classePage);
    }

    @GetMapping("/nom")
    public ResponseEntity<List<Classe>> getClasseByNom(@RequestParam String nom) {
        List<Classe> classes = classeService.getClasseByNomContaining(nom);
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/nomAndNumSalle")
    public ResponseEntity<List<Classe>> getClasseByNomAndNumSalle(
            @RequestParam String nom, 
            @RequestParam String numSalle) {
        List<Classe> classes = classeService.getClasseByNomAndNumSalle(nom, numSalle);
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classe> getClasseById(@PathVariable Long id) {
        Classe classe = classeService.getClasseById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Classe not found with id: " + id));
        return ResponseEntity.ok(classe);
    }

    @PostMapping
    public ResponseEntity<Classe> createClasse(@Valid @RequestBody Classe classe) {
        // Add validation if needed
        if (classe.getNom() == null || classe.getNom().trim().isEmpty()) {
            throw new ValidationException("Le nom de la classe est obligatoire");
        }
        if (classe.getNumSalle() == null || classe.getNumSalle().trim().isEmpty()) {
            throw new ValidationException("Le numéro de salle est obligatoire");
        }
        
        Classe newClasse = classeService.createClasse(classe);
        return ResponseEntity.ok(newClasse);
    }
   
    @PostMapping("/multi")
    public ResponseEntity<List<Classe>> createMultipleClasses(@Valid @RequestBody List<Classe> classes) {
        // Add validation if needed
        for (Classe classe : classes) {
            if (classe.getNom() == null || classe.getNom().trim().isEmpty()) {
                throw new ValidationException("Le nom de la classe est obligatoire");
            }
            if (classe.getNumSalle() == null || classe.getNumSalle().trim().isEmpty()) {
                throw new ValidationException("Le numéro de salle est obligatoire");
            }
        }
        
        List<Classe> newClasses = classeService.createMultipleClasses(classes);
        return ResponseEntity.ok(newClasses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Classe> updateClasse(@PathVariable Long id, @Valid @RequestBody Classe classe) {
        Classe updatedClasse = classeService.updateClasse(id, classe);
        if (updatedClasse == null) {
            throw new ResourceNotFoundException("Classe not found with id: " + id);
        }
        return ResponseEntity.ok(updatedClasse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClasse(@PathVariable Long id) {
        if (!classeService.getClasseById(id).isPresent()) {
            throw new ResourceNotFoundException("Classe not found with id: " + id);
        }
        classeService.deleteClasse(id);
        return ResponseEntity.ok().build();
    }
}
