package com.Formation.formationapi.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Formation.formationapi.Modele.entity.Classe;

@Repository

public interface ClasseRepository extends JpaRepository<Classe, Long>  {


    List<Classe> findByNom(String nom);
    List<Classe> findByNomAndNumSalle(String nom, String numSalle);


}
