package com.Formation.Repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Formation.Modele.entity.Formation;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {

    List<Formation> findByStatut(String statut);
    List<Formation> findByDateDebutBetween(Date dateDebut, Date dateFin);
}
