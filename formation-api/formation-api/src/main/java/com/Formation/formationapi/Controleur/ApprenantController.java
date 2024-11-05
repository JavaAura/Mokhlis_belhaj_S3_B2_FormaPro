package com.Formation.formationapi.Controleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Formation.formationapi.service.ApprenantService;
import com.Formation.formationapi.Modele.entity.Apprenant;
import com.Formation.formationapi.Exceptions.ResourceNotFoundException;
import com.Formation.formationapi.Exceptions.ValidationException;


import javax.validation.Valid;

@RestController
@RequestMapping("api/apprenants")
public class ApprenantController {

    private final ApprenantService apprenantService;

    @Autowired
    public ApprenantController(ApprenantService apprenantService) {
        this.apprenantService = apprenantService;
    }

    @GetMapping
    public ResponseEntity<Page<Apprenant>> getAllApprenants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Apprenant> apprenants = apprenantService.getAllApprenants(pageable);
        return ResponseEntity.ok(apprenants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apprenant> getApprenantById(@PathVariable Long id) {
        Apprenant apprenant = apprenantService.getApprenantById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Apprenant not found with id: " + id));
        return ResponseEntity.ok(apprenant);
    }

    @PostMapping
    public ResponseEntity<Apprenant> createApprenant(@Valid @RequestBody Apprenant apprenant) {
        // Check if email already exists
        if (!apprenantService.getApprenantByEmail(apprenant.getEmail()).isEmpty()) {
            throw new ValidationException("Email already exists: " + apprenant.getEmail());
        }
        Apprenant newApprenant = apprenantService.createApprenant(apprenant);
        return ResponseEntity.ok(newApprenant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Apprenant> updateApprenant(@PathVariable Long id, @RequestBody Apprenant apprenantDetails) {
        Apprenant apprenant = apprenantService.getApprenantById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Apprenant not found with id: " + id));
        
        // Check if new email already exists (if email is being updated)
        if (apprenantDetails.getEmail() != null && 
            !apprenantDetails.getEmail().equals(apprenant.getEmail()) && 
            !apprenantService.getApprenantByEmail(apprenantDetails.getEmail()).isEmpty()) {
            throw new ValidationException("Email already exists: " + apprenantDetails.getEmail());
        }

        if (apprenantDetails.getNom() != null) {
            apprenant.setNom(apprenantDetails.getNom());
        }
        if (apprenantDetails.getPrenom() != null) {
            apprenant.setPrenom(apprenantDetails.getPrenom());
        }
        if (apprenantDetails.getEmail() != null) {
            apprenant.setEmail(apprenantDetails.getEmail());
        }
        if (apprenantDetails.getNiveau() != null) {
            apprenant.setNiveau(apprenantDetails.getNiveau());
        }
        if (apprenantDetails.getFormation() != null) {
            apprenant.setFormation(apprenantDetails.getFormation());
        }
        if (apprenantDetails.getClasse() != null) {
            apprenant.setClasse(apprenantDetails.getClasse());
        }
        
        return ResponseEntity.ok(apprenantService.updateApprenant(apprenant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApprenant(@PathVariable Long id) {
        if (!apprenantService.getApprenantById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        apprenantService.deleteApprenant(id);
        return ResponseEntity.ok().build();
    }
}
