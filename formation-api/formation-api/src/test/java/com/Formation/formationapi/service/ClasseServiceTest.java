package com.Formation.formationapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.Formation.formationapi.Modele.entity.Classe;
import com.Formation.formationapi.Modele.entity.Formateur;
import com.Formation.formationapi.Repositories.ClasseRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClasseServiceTest {

    @Mock
    private ClasseRepository classeRepository;

    @InjectMocks
    private ClasseService classeService;

    private Classe classe;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        classe = new Classe();
        classe.setId(1L);
        classe.setNom("Classe Test");
        classe.setNumSalle("A101");
        
        // Create a formateur for the classe
        Formateur formateur = new Formateur();
        formateur.setId(1L);
        formateur.setNom("Dupont");
        formateur.setPrenom("Jean");
        classe.setFormateur(formateur);
        
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void getAllClasses_ShouldReturnPageOfClasses() {
        // Arrange
        Page<Classe> expectedPage = new PageImpl<>(Arrays.asList(classe));
        when(classeRepository.findAll(pageable)).thenReturn(expectedPage);

        // Act
        Page<Classe> actualPage = classeService.getAllClasses(pageable);

        // Assert
        assertEquals(expectedPage, actualPage);
        verify(classeRepository).findAll(pageable);
    }

    @Test
    void getClasseById_ShouldReturnClasse() {
        // Arrange
        when(classeRepository.findById(1L)).thenReturn(Optional.of(classe));

        // Act
        Optional<Classe> result = classeService.getClasseById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(classe, result.get());
        verify(classeRepository).findById(1L);
    }

    @Test
    void getClasseByNom_ShouldReturnListOfClasses() {
        // Arrange
        List<Classe> expectedClasses = Arrays.asList(classe);
        when(classeRepository.findByNom("Classe Test")).thenReturn(expectedClasses);

        // Act
        List<Classe> actualClasses = classeService.getClasseByNom("Classe Test");

        // Assert
        assertEquals(expectedClasses, actualClasses);
        verify(classeRepository).findByNom("Classe Test");
    }

    @Test
    void createClasse_ShouldReturnSavedClasse() {
        // Arrange
        when(classeRepository.save(any(Classe.class))).thenReturn(classe);

        // Act
        Classe savedClasse = classeService.createClasse(classe);

        // Assert
        assertNotNull(savedClasse);
        assertEquals(classe, savedClasse);
        verify(classeRepository).save(classe);
    }

    @Test
    void updateClasse_ShouldReturnUpdatedClasse() {
        // Arrange
        Classe updatedClasse = new Classe();
        updatedClasse.setNom("Updated Classe");
        updatedClasse.setNumSalle("B202");
        
        when(classeRepository.findById(1L)).thenReturn(Optional.of(classe));
        when(classeRepository.save(any(Classe.class))).thenReturn(updatedClasse);

        // Act
        Classe result = classeService.updateClasse(1L, updatedClasse);

        // Assert
        assertNotNull(result);
        assertEquals(updatedClasse.getNom(), result.getNom());
        assertEquals(updatedClasse.getNumSalle(), result.getNumSalle());
        verify(classeRepository).findById(1L);
        verify(classeRepository).save(any(Classe.class));
    }

    @Test
    void deleteClasse_ShouldCallRepositoryDelete() {
        // Arrange
        Long id = 1L;

        // Act
        classeService.deleteClasse(id);

        // Assert
        verify(classeRepository).deleteById(id);
    }

    @Test
    void createMultipleClasses_ShouldReturnListOfSavedClasses() {
        // Arrange
        List<Classe> classes = Arrays.asList(classe);
        when(classeRepository.saveAll(anyList())).thenReturn(classes);

        // Act
        List<Classe> savedClasses = classeService.createMultipleClasses(classes);

        // Assert
        assertNotNull(savedClasses);
        assertEquals(classes.size(), savedClasses.size());
        assertEquals(classes, savedClasses);
        verify(classeRepository).saveAll(classes);
    }
} 