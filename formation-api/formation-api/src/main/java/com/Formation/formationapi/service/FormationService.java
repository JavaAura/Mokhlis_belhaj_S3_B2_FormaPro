package com.Formation.formationapi.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Formation.formationapi.Modele.entity.Formation;
import com.Formation.formationapi.Repositories.FormationRepository;

@Service
@Transactional
public class FormationService {
    private final FormationRepository formationRepository;

    @Autowired
    public FormationService(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    public List<Formation> getFormationsByDate(Date dateDebut, Date dateFin) {
        return formationRepository.findByDateDebutBetween(dateDebut, dateFin);
    }

    public Optional<Formation> getFormationById(Long id) {
        return formationRepository.findById(id);
    }

    public Formation saveFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    public void deleteFormation(Long id) {
        formationRepository.deleteById(id);
    }
}
