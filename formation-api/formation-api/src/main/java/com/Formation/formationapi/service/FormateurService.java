package com.Formation.formationapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Formation.formationapi.Modele.entity.Formateur;
import com.Formation.formationapi.Repositories.FormateurRepository;



@Service
	
public class FormateurService {

    private final FormateurRepository formateurRepository;

    @Autowired
    public FormateurService(FormateurRepository formateurRepository) {
        this.formateurRepository = formateurRepository;
    }

    public List<Formateur> getAllFormateurs() {
        return formateurRepository.findAll();
    }

    public List<Formateur> getFormateurBySpecialite(String specialite) {
        return formateurRepository.findBySpecialite(specialite);
    }

    public Optional<Formateur> getFormateurById(Long id) {
        return formateurRepository.findById(id);
    }

    public Formateur createFormateur(Formateur formateur) {
        return formateurRepository.save(formateur);
    }

    public Formateur updateFormateur(Formateur formateur) {
        return formateurRepository.save(formateur);
    }

    public void deleteFormateur(Long id) {
        formateurRepository.deleteById(id);
    }

}
