package com.Formation.formationapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.Formation.formationapi.Modele.entity.Classe;
import com.Formation.formationapi.Repositories.ClasseRepository;


@Service
public class ClasseService {

    @Autowired
    private ClasseRepository classeRepository;
    
    public Page<Classe> getAllClasses(Pageable pageable) {
        return classeRepository.findAll(pageable);
    }

    public List<Classe> getClasseByNom(String nom) {
        return classeRepository.findByNom(nom);
    }

    public List<Classe> getClasseByNomContaining(String nom) {
        return classeRepository.findByNomContaining(nom);
    }

    public List<Classe> getClasseByNomAndNumSalle(String nom, String numSalle) {
        return classeRepository.findByNomAndNumSalle(nom, numSalle);
    }

    public Optional<Classe> getClasseById(Long id) {
        return classeRepository.findById(id);
    }

    public Classe createClasse(Classe classe) {
        return classeRepository.save(classe);
    }

    public List<Classe> createMultipleClasses(List<Classe> classes) {
        return classeRepository.saveAll(classes);
    }

    public Classe updateClasse(Long id, Classe classe) {
        Optional<Classe> existingClasse = classeRepository.findById(id);
        if (existingClasse.isPresent()) {
            Classe updatedClasse = existingClasse.get();
            if (classe.getNom() != null) {
                updatedClasse.setNom(classe.getNom());
            }
            if (classe.getNumSalle() != null) {
                updatedClasse.setNumSalle(classe.getNumSalle());
            }
            if (classe.getFormateur() != null) {
                updatedClasse.setFormateur(classe.getFormateur());
            }
            
            
            return classeRepository.save(updatedClasse);
        }
        return null;
    }

    public void deleteClasse(Long id) {
        classeRepository.deleteById(id);
    }

  

}
