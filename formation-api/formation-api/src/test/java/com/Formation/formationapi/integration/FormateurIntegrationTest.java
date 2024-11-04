package com.Formation.formationapi.integration;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.Formation.formationapi.Modele.entity.Classe;
import com.Formation.formationapi.Modele.entity.Formateur;
import com.Formation.formationapi.Repositories.ClasseRepository;
import com.Formation.formationapi.Repositories.FormateurRepository;
import com.Formation.formationapi.service.FormateurService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FormateurIntegrationTest {

    @Autowired
    private FormateurService formateurService;

    @Autowired
    private FormateurRepository formateurRepository;

    @Autowired
    private ClasseRepository classeRepository;

    private Formateur formateur;
    private Classe classe;

    @BeforeEach
    void setUp() {
        // Clean up the database before each test
        classeRepository.deleteAll();
        formateurRepository.deleteAll();

        // Create a test formateur
        formateur = new Formateur();
        formateur.setNom("Dupont");
        formateur.setPrenom("Jean");
        formateur.setEmail("jean.dupont@test.com");
        formateur.setSpecialite("Java");
        formateur = formateurRepository.save(formateur);

        // Create a test classe
        classe = new Classe();
        classe.setNom("Classe Test");
        classe.setNumSalle("A101");
        classe.setFormateur(formateur);
        classe = classeRepository.save(classe);
    }

    @Test
    void createFormateur_ShouldSaveFormateurCorrectly() {
        // Arrange
        Formateur newFormateur = new Formateur();
        newFormateur.setNom("Martin");
        newFormateur.setPrenom("Pierre");
        newFormateur.setEmail("pierre.martin@test.com");
        newFormateur.setSpecialite("Python");

        // Act
        Formateur savedFormateur = formateurService.createFormateur(newFormateur);

        // Assert
        assertNotNull(savedFormateur.getId());
        assertEquals("Martin", savedFormateur.getNom());
        assertEquals("Pierre", savedFormateur.getPrenom());
        assertEquals("Python", savedFormateur.getSpecialite());

        // Verify it's in the database
        assertTrue(formateurRepository.findById(savedFormateur.getId()).isPresent());
    }

    @Test
    void updateFormateur_ShouldUpdateCorrectly() {
        // Arrange
        Formateur updateRequest = new Formateur();
        updateRequest.setId(formateur.getId());
        updateRequest.setNom("Dupont-Updated");
        updateRequest.setPrenom("Jean");
        updateRequest.setEmail("new.email@test.com");
        updateRequest.setSpecialite("Spring Boot");

        // Act
        Formateur updatedFormateur = formateurService.updateFormateur(updateRequest);

        // Assert
        assertNotNull(updatedFormateur);
        assertEquals("Dupont-Updated", updatedFormateur.getNom());
        assertEquals("Jean", updatedFormateur.getPrenom());
        assertEquals("new.email@test.com", updatedFormateur.getEmail());
        assertEquals("Spring Boot", updatedFormateur.getSpecialite());

        // Verify the update is persisted
        Formateur persistedFormateur = formateurRepository.findById(formateur.getId()).orElse(null);
        assertNotNull(persistedFormateur);
        assertEquals("Dupont-Updated", persistedFormateur.getNom());
    }

  
    @Test
    void deleteFormateur_ShouldRemoveFormateurAndUpdateClasses() {
        classe.setFormateur(null);
        classeRepository.save(classe);

        // Act
        formateurService.deleteFormateur(formateur.getId());

        // Assert
        assertFalse(formateurRepository.existsById(formateur.getId()));
        
        // Verify that the classe's formateur reference is updated
        Classe updatedClasse = classeRepository.findById(classe.getId()).orElse(null);
        assertNotNull(updatedClasse);
        assertNull(updatedClasse.getFormateur());
    }

  

    @Test
    void findBySpecialite_ShouldReturnMatchingFormateurs() {
        // Arrange
        Formateur formateur2 = new Formateur();
        formateur2.setNom("Martin");
        formateur2.setPrenom("Sophie");
        formateur2.setEmail("sophie.martin@test.com");
        formateur2.setSpecialite("Java");
        formateurRepository.save(formateur2);

        // Act
        List<Formateur> javaFormateurs = formateurService.getFormateurBySpecialite("Java");

        // Assert
        assertEquals(2, javaFormateurs.size());
        assertTrue(javaFormateurs.stream()
                .allMatch(f -> f.getSpecialite().equals("Java")));
    }
} 