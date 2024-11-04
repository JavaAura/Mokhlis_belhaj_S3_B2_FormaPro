package com.Formation.formationapi.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.Formation.formationapi.Modele.Enum.StatutFormation;
import com.Formation.formationapi.Modele.entity.Apprenant;
import com.Formation.formationapi.Modele.entity.Classe;
import com.Formation.formationapi.Modele.entity.Formation;
import com.Formation.formationapi.Repositories.ApprenantRepository;
import com.Formation.formationapi.Repositories.ClasseRepository;
import com.Formation.formationapi.Repositories.FormationRepository;
import com.Formation.formationapi.service.ApprenantService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ApprenantIntegrationTest {

    @Autowired
    private ApprenantService apprenantService;

    @Autowired
    private ApprenantRepository apprenantRepository;

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private FormationRepository formationRepository;

    private Apprenant apprenant;
    private Classe classe;
    private Formation formation;

    @BeforeEach
    void setUp() {
        // Nettoyer la base de données
        apprenantRepository.deleteAll();
        classeRepository.deleteAll();
        formationRepository.deleteAll();
            // Créer une classe de test
            classe = new Classe();
            classe.setNom("Classe Test");
        classe.setNumSalle("A101");
        classe = classeRepository.save(classe);
  
        // Créer une formation de test
        formation = new Formation();
        formation.setTitre("Formation Test");
        formation.setNiveau("Débutant");
        formation.setStatut(StatutFormation.PLANIFIEE);
        formation.setPrerequis("Aucun");
        formation.setCapaciteMax(10);
        formation.setCapaciteMin(1);
        // use java.util.Date + 1 jour
        formation.setDateDebut(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
        // use java.util.Date + 2 jours
        formation.setDateFin(new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000));
        formation = formationRepository.save(formation);
 
        // Créer un apprenant de test
        apprenant = new Apprenant();
        apprenant.setNom("Dupont");
        apprenant.setPrenom("Jean");
        apprenant.setEmail("jean.dupont@test.com");
        apprenant.setNiveau("debutant");
      
        apprenant = apprenantRepository.save(apprenant);
    }

    @Test
    void getAllApprenants_ShouldReturnPageOfApprenants() {
        // Act
        Page<Apprenant> apprenantPage = apprenantService.getAllApprenants(PageRequest.of(0, 10));

        // Assert
        assertNotNull(apprenantPage);
        assertFalse(apprenantPage.getContent().isEmpty());
        assertEquals(1, apprenantPage.getContent().size());
        assertEquals("Dupont", apprenantPage.getContent().get(0).getNom());
    }

    @Test
    void getApprenantById_ShouldReturnCorrectApprenant() {
        // Act
        Optional<Apprenant> foundApprenant = apprenantService.getApprenantById(apprenant.getId());

        // Assert
        assertTrue(foundApprenant.isPresent());
        assertEquals(apprenant.getNom(), foundApprenant.get().getNom());
        assertEquals(apprenant.getEmail(), foundApprenant.get().getEmail());
    }

    @Test
    void getApprenantByNom_ShouldReturnMatchingApprenants() {
        // Act
        List<Apprenant> apprenants = apprenantService.getApprenantByNom("Dupont");

        // Assert
        assertFalse(apprenants.isEmpty());
        assertEquals(1, apprenants.size());
        assertEquals("Dupont", apprenants.get(0).getNom());
    }

    @Test
    void getApprenantByEmail_ShouldReturnMatchingApprenants() {
        // Act
        List<Apprenant> apprenants = apprenantService.getApprenantByEmail("jean.dupont@test.com");

        // Assert
        assertFalse(apprenants.isEmpty());
        assertEquals(1, apprenants.size());
        assertEquals("jean.dupont@test.com", apprenants.get(0).getEmail());
    }

    @Test
    void createApprenant_ShouldSaveApprenantCorrectly() {
        // Arrange
        Apprenant newApprenant = new Apprenant();
        newApprenant.setNom("Martin");
        newApprenant.setPrenom("Jean");
        newApprenant.setEmail("jean.martin@test.com");
        newApprenant.setNiveau("debutant");
        newApprenant.setClasse(classe);
        newApprenant.setFormation(formation);

        // Act
        Apprenant savedApprenant = apprenantService.createApprenant(newApprenant);

        // Assert
        assertNotNull(savedApprenant);
        assertEquals("Martin", savedApprenant.getNom());
        assertEquals("jean.martin@test.com", savedApprenant.getEmail());
    }
} 