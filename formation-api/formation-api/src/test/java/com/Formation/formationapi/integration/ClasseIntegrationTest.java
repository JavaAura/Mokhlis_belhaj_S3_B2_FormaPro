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

import com.Formation.formationapi.Modele.entity.Classe;
import com.Formation.formationapi.Modele.entity.Formateur;
import com.Formation.formationapi.Repositories.ClasseRepository;
import com.Formation.formationapi.Repositories.FormateurRepository;
import com.Formation.formationapi.service.ClasseService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ClasseIntegrationTest {

    @Autowired
    private ClasseService classeService;

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private FormateurRepository formateurRepository;

    private Classe classe;
    private Formateur formateur;

    @BeforeEach
    void setUp() {
        // Nettoyer la base de données avant chaque test
        classeRepository.deleteAll();
        formateurRepository.deleteAll();

        // Créer un formateur de test
        formateur = new Formateur();
        formateur.setNom("Dupont");
        formateur.setPrenom("Jean");
        formateur.setEmail("jean.dupont@test.com");
        formateur.setSpecialite("Java");
        formateur = formateurRepository.save(formateur);

        // Créer une classe de test
        classe = new Classe();
        classe.setNom("Classe Test");
        classe.setNumSalle("A101");
        classe.setFormateur(formateur);
        classe = classeRepository.save(classe);
    }

    @Test
    void createClasse_ShouldSaveClasseCorrectly() {
        // Arrange
        Classe newClasse = new Classe();
        newClasse.setNom("Nouvelle Classe");
        newClasse.setNumSalle("B202");
        newClasse.setFormateur(formateur);

        // Act
        Classe savedClasse = classeService.createClasse(newClasse);

        // Assert
        assertNotNull(savedClasse.getId());
        assertEquals("Nouvelle Classe", savedClasse.getNom());
        assertEquals("B202", savedClasse.getNumSalle());
        assertEquals(formateur.getId(), savedClasse.getFormateur().getId());
    }

    @Test
    void getAllClasses_ShouldReturnPageOfClasses() {
        // Act
        Page<Classe> classePage = classeService.getAllClasses(PageRequest.of(0, 10));

        // Assert
        assertNotNull(classePage);
        assertTrue(classePage.getContent().size() > 0);
        assertEquals(classe.getNom(), classePage.getContent().get(0).getNom());
    }

    @Test
    void getClasseById_ShouldReturnCorrectClasse() {
        // Act
        Optional<Classe> foundClasse = classeService.getClasseById(classe.getId());

        // Assert
        assertTrue(foundClasse.isPresent());
        assertEquals(classe.getNom(), foundClasse.get().getNom());
        assertEquals(classe.getNumSalle(), foundClasse.get().getNumSalle());
    }

    @Test
    void updateClasse_ShouldUpdateCorrectly() {
        // Arrange
        Classe updateRequest = new Classe();
        updateRequest.setNom("Classe Updated");
        updateRequest.setNumSalle("C303");

        // Act
        Classe updatedClasse = classeService.updateClasse(classe.getId(), updateRequest);

        // Assert
        assertNotNull(updatedClasse);
        assertEquals("Classe Updated", updatedClasse.getNom());
        assertEquals("C303", updatedClasse.getNumSalle());

        // Verify the update is persisted
        Classe persistedClasse = classeRepository.findById(classe.getId()).orElse(null);
        assertNotNull(persistedClasse);
        assertEquals("Classe Updated", persistedClasse.getNom());
    }

    @Test
    void deleteClasse_ShouldRemoveClasse() {
        // Act
        classeService.deleteClasse(classe.getId());

        // Assert
        assertFalse(classeRepository.existsById(classe.getId()));
    }

    @Test
    void createMultipleClasses_ShouldSaveAllClasses() {
        // Arrange
        Classe classe1 = new Classe();
        classe1.setNom("Classe 1");
        classe1.setNumSalle("D404");
        classe1.setFormateur(formateur);

        Classe classe2 = new Classe();
        classe2.setNom("Classe 2");
        classe2.setNumSalle("E505");
        classe2.setFormateur(formateur);

        List<Classe> classes = Arrays.asList(classe1, classe2);

        // Act
        List<Classe> savedClasses = classeService.createMultipleClasses(classes);

        // Assert
        assertEquals(2, savedClasses.size());
        assertTrue(savedClasses.stream().allMatch(c -> c.getId() != null));
    }

    @Test
    void getClasseByNom_ShouldReturnMatchingClasses() {
        // Act
        List<Classe> classes = classeService.getClasseByNom(classe.getNom());

        // Assert
        assertFalse(classes.isEmpty());
        assertEquals(classe.getNom(), classes.get(0).getNom());
    }

    @Test
    void getClasseByNomContaining_ShouldReturnMatchingClasses() {
        // Act
        List<Classe> classes = classeService.getClasseByNomContaining("Test");

        // Assert
        assertFalse(classes.isEmpty());
        assertTrue(classes.get(0).getNom().contains("Test"));
    }

    @Test
    void getClasseByNomAndNumSalle_ShouldReturnMatchingClasses() {
        // Act
        List<Classe> classes = classeService.getClasseByNomAndNumSalle(
            classe.getNom(), 
            classe.getNumSalle()
        );

        // Assert
        assertFalse(classes.isEmpty());
        assertEquals(classe.getNom(), classes.get(0).getNom());
        assertEquals(classe.getNumSalle(), classes.get(0).getNumSalle());
    }
} 