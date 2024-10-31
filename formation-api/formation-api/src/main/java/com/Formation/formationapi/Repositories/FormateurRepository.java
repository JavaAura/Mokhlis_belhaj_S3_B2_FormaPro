package com.Formation.formationapi.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Formation.formationapi.Modele.entity.Formateur;

@Repository
public interface FormateurRepository extends JpaRepository<Formateur, Long> {

    List<Formateur> findBySpecialite(String specialite);
}
