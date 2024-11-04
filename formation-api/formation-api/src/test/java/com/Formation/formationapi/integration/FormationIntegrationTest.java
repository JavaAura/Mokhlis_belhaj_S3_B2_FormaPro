package com.Formation.formationapi.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.Formation.formationapi.Modele.entity.Formation;
import com.Formation.formationapi.Modele.Enum.StatutFormation;
import com.Formation.formationapi.Modele.entity.Formateur;
import com.Formation.formationapi.Repositories.FormationRepository;
import com.Formation.formationapi.Repositories.FormateurRepository;
import com.Formation.formationapi.service.FormationService;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FormationIntegrationTest {

    @Autowired
    private FormationService formationService;

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private FormateurRepository formateurRepository;

    private Formation formation;
    private Formateur formateur;
    private Date dateDebut;
    private Date dateFin;

    @BeforeEach
    void setUp() {
        // Nettoyer la base de données
        formationRepository.deleteAll();
        formateurRepository.deleteAll();

        // Créer un formateur
        formateur = new Formateur();
        formateur.setNom("Dupont");
        formateur.setPrenom("Jean");
        formateur.setEmail("jean.dupont@test.com");
        formateur.setSpecialite("Java");
        formateur = formateurRepository.save(formateur);

        // Préparer les dates
        Calendar cal = Calendar.getInstance();
        dateDebut = cal.getTime();
        cal.add(Calendar.MONTH, 3);
        dateFin = cal.getTime();

        // Créer une formation de test
        formation = new Formation();
        formation.setTitre("Formation Java");
        formation.setDateDebut(dateDebut);
        formation.setDateFin(dateFin);
        formation.setNiveau("Débutant");
        formation.setPrerequis("Aucun");
        formation.setCapaciteMin(5);
        formation.setCapaciteMax(20);
        formation.setStatut(StatutFormation.PLANIFIEE);
        formation.setFormateur(formateur);
        formation = formationRepository.save(formation);
    }

    @Test
    void getAllFormations_ShouldReturnAllFormations() {
        // Act
        List<Formation> formations = formationService.getAllFormations();

        // Assert
        assertFalse(formations.isEmpty());
        assertEquals(1, formations.size());
        assertEquals("Formation Java", formations.get(0).getTitre());
    }

   
    @Test
    void getFormationById_ShouldReturnCorrectFormation() {
        // Act
        Optional<Formation> foundFormation = formationService.getFormationById(formation.getId());

        // Assert
        assertTrue(foundFormation.isPresent());
        assertEquals(formation.getTitre(), foundFormation.get().getTitre());
        assertEquals(formation.getNiveau(), foundFormation.get().getNiveau());
    }

    @Test
    void saveFormation_ShouldCreateNewFormation() {
        // Arrange
        Formation newFormation = new Formation();
        newFormation.setTitre("Formation Spring");
        newFormation.setDateDebut(dateDebut);
        newFormation.setDateFin(dateFin);
        newFormation.setNiveau("Intermédiaire");
        newFormation.setPrerequis("Java");
        newFormation.setCapaciteMin(5);
        newFormation.setCapaciteMax(15);
        newFormation.setStatut(StatutFormation.PLANIFIEE);
        newFormation.setFormateur(formateur);

        // Act
        Formation savedFormation = formationService.saveFormation(newFormation);

        // Assert
        assertNotNull(savedFormation.getId());
        assertEquals("Formation Spring", savedFormation.getTitre());
        assertEquals("Intermédiaire", savedFormation.getNiveau());
    }

    @Test
    void updateFormation_ShouldUpdateExistingFormation() {
        // Arrange
        formation.setTitre("Formation Java Updated");
        formation.setNiveau("Avancé");

        // Act
        Formation updatedFormation = formationService.saveFormation(formation);

        // Assert
        assertEquals("Formation Java Updated", updatedFormation.getTitre());
        assertEquals("Avancé", updatedFormation.getNiveau());

        // Verify persistence
        Optional<Formation> persistedFormation = formationRepository.findById(formation.getId());
        assertTrue(persistedFormation.isPresent());
        assertEquals("Formation Java Updated", persistedFormation.get().getTitre());
    }

    @Test
    void deleteFormation_ShouldRemoveFormation() {
        // Act
        formationService.deleteFormation(formation.getId());

        // Assert
        assertFalse(formationRepository.existsById(formation.getId()));
    }

    @Test
    void saveFormation_ShouldValidateCapacityConstraints() {
        // Arrange
        Formation invalidFormation = new Formation();
        invalidFormation.setTitre("Formation Test");
        invalidFormation.setDateDebut(dateDebut);
        invalidFormation.setDateFin(dateFin);
        invalidFormation.setNiveau("Débutant");
        invalidFormation.setPrerequis("Aucun");
        invalidFormation.setCapaciteMin(0); // Invalid capacity
        invalidFormation.setCapaciteMax(0); // Invalid capacity
        invalidFormation.setStatut(StatutFormation.PLANIFIEE);
        invalidFormation.setFormateur(formateur);

        // Act & Assert
        assertThrows(Exception.class, () -> {
            formationService.saveFormation(invalidFormation);
        });
    }
} 