package com.Formation.formationapi.Controleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Formation.formationapi.service.ClasseService;
import com.Formation.formationapi.Modele.entity.Classe;

import java.util.List;
import java.util.Optional;

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
    public List<Classe> getClasseByNom(@RequestParam String nom) {
        return classeService.getClasseByNomContaining(nom);
    }

    @GetMapping("/nomAndNumSalle")
    public List<Classe> getClasseByNomAndNumSalle(@RequestParam String nom, @RequestParam String numSalle) {
        return classeService.getClasseByNomAndNumSalle(nom, numSalle);
    }

    @GetMapping("/{id}")
    public Optional<Classe> getClasseById(@PathVariable Long id) {
        return classeService.getClasseById(id);
    }

    @PostMapping
    public Classe createClasse(@Valid @RequestBody Classe classe) {
        return classeService.createClasse(classe);
    }
   
    @PostMapping("/multi")
    public List<Classe> createMultipleClasses(@Valid @RequestBody List<Classe> classes) {
        return classeService.createMultipleClasses(classes);
    }

    @PutMapping("/{id}")
    public Classe updateClasse(@PathVariable Long id, @RequestBody Classe classe) {
        return classeService.updateClasse(id,classe);
    }


    @DeleteMapping("/{id}")
    public void deleteClasse(@PathVariable Long id) {
        classeService.deleteClasse(id);
    }

}
