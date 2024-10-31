package com.Formation.formationapi.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Formation.formationapi.Modele.entity.Classe;
import com.Formation.formationapi.Repositories.ClasseRepository;

@Service
public class ClasseService {

    @Autowired
    private ClasseRepository classeRepository;

    public List<Classe> getAllClasses() {
        return classeRepository.findAll();
    }

    public List<Classe> getClasseByNom(String nom) {
        return classeRepository.findByNom(nom);
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

    public Classe updateClasse(Classe classe) {
        return classeRepository.save(classe);
    }

    public void deleteClasse(Long id) {
        classeRepository.deleteById(id);
    }

}
