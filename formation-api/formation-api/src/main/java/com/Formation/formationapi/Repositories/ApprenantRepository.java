package com.Formation.formationapi.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Formation.formationapi.Modele.entity.Apprenant;

@Repository
public interface ApprenantRepository extends JpaRepository<Apprenant, Long> {

    List<Apprenant> findByNom(String nom);

    List<Apprenant> findByEmail(String email);

}
