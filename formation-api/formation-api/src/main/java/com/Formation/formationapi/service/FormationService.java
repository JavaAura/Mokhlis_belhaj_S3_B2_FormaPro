package com.Formation.formationapi.service;

import java.util.List;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Formation.formationapi.Modele.Enum.StatutFormation;
import com.Formation.formationapi.Modele.entity.Formation;
import com.Formation.formationapi.Repositories.FormationRepository;



@Service


public class FormationService {


    private final FormationRepository formationRepository; 
    
    @Autowired
    public FormationService(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }
    
    

    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    public Optional<Formation> getFormationById(Long id) {
        return formationRepository.findById(id);
    }

    public List<Formation> getFormationByStatut(String statut) {
        return formationRepository.findByStatut(statut);
    }

    public List<Formation> getFormationByDateDebutBetween(Date dateDebut, Date dateFin) {
        return formationRepository.findByDateDebutBetween(dateDebut, dateFin);
    }

    public Formation createFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    public Formation updateFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    public void deleteFormation(Long id) {
        formationRepository.deleteById(id);
    }

    public Formation updateFormationStatus(Long id, String newStatus) {
        Optional<Formation> formationOpt = formationRepository.findById(id);
        if (formationOpt.isPresent()) {
            Formation formation = formationOpt.get();
            formation.setStatut(StatutFormation.valueOf(newStatus));
            return formationRepository.save(formation);
        }
        throw new RuntimeException("Formation non trouvée avec l'ID : " + id);
    }

}
