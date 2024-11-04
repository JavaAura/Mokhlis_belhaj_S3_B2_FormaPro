package com.Formation.formationapi.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Formation.formationapi.Modele.entity.Apprenant;
import com.Formation.formationapi.Repositories.ApprenantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ApprenantService {

    @Autowired
    private ApprenantRepository apprenantRepository;

    public Page<Apprenant> getAllApprenants(Pageable pageable) {
        return apprenantRepository.findAll(pageable);
    }

    public Optional<Apprenant> getApprenantById(Long id) {
        return apprenantRepository.findById(id);
    }

    public List<Apprenant> getApprenantByNom(String nom) {
        return apprenantRepository.findByNom(nom);
    }

    public List<Apprenant> getApprenantByEmail(String email) {
        return apprenantRepository.findByEmail(email);
    }

    public Apprenant createApprenant(Apprenant apprenant) {
        return apprenantRepository.save(apprenant);
    }

    public Apprenant updateApprenant(Apprenant apprenant) {
        return apprenantRepository.save(apprenant);
    }

    public void deleteApprenant(Long id) {
        apprenantRepository.deleteById(id);
    }
}
