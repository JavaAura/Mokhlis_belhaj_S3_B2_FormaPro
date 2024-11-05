package com.Formation.formationapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Formation.formationapi.Modele.entity.Formateur;
import com.Formation.formationapi.Repositories.FormateurRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FormateurServiceTest {

    @Mock
    private FormateurRepository formateurRepository;

    @InjectMocks
    private FormateurService formateurService;

    private Formateur formateur;

    @BeforeEach
    void setUp() {
        formateur = new Formateur();
        formateur.setId(1L);
        formateur.setNom("Dupont");
        formateur.setPrenom("Jean");
        formateur.setEmail("jean.dupont@test.com");
        formateur.setSpecialite("Java");
    }

    @Test
    void getAllFormateurs_ShouldReturnListOfFormateurs() {
        // Arrange
        List<Formateur> expectedFormateurs = Arrays.asList(formateur);
        Page<Formateur> expectedPage = new PageImpl<>(expectedFormateurs);
        Pageable pageable = PageRequest.of(0, 10);
        when(formateurRepository.findAll(pageable)).thenReturn(expectedPage);

        // Act
        Page<Formateur> actualFormateurs = formateurService.getAllFormateurs(pageable);

        // Assert
        assertEquals(expectedFormateurs, actualFormateurs.getContent());
        assertEquals(1, actualFormateurs.getTotalPages());
        verify(formateurRepository).findAll(pageable);
    }

    @Test
    void getFormateurById_ShouldReturnFormateur() {
        // Arrange
        when(formateurRepository.findById(1L)).thenReturn(Optional.of(formateur));

        // Act
        Optional<Formateur> result = formateurService.getFormateurById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(formateur, result.get());
        verify(formateurRepository).findById(1L);
    }

    @Test
    void createFormateur_ShouldReturnSavedFormateur() {
        // Arrange
        when(formateurRepository.save(any(Formateur.class))).thenReturn(formateur);

        // Act
        Formateur savedFormateur = formateurService.createFormateur(formateur);

        // Assert
        assertNotNull(savedFormateur);
        assertEquals(formateur, savedFormateur);
        verify(formateurRepository).save(formateur);
    }

    @Test
    void getFormateurBySpecialite_ShouldReturnListOfFormateurs() {
        // Arrange
        List<Formateur> expectedFormateurs = Arrays.asList(formateur);
        when(formateurRepository.findBySpecialite("Java")).thenReturn(expectedFormateurs);

        // Act
        List<Formateur> actualFormateurs = formateurService.getFormateurBySpecialite("Java");

        // Assert
        assertEquals(expectedFormateurs, actualFormateurs);
        verify(formateurRepository).findBySpecialite("Java");
    }

    @Test
    void updateFormateur_ShouldReturnUpdatedFormateur() {
        // Arrange
        when(formateurRepository.save(any(Formateur.class))).thenReturn(formateur);

        // Act
        Formateur updatedFormateur = formateurService.updateFormateur(formateur);

        // Assert
        assertNotNull(updatedFormateur);
        assertEquals(formateur, updatedFormateur);
        verify(formateurRepository).save(formateur);
    }

    @Test
    void deleteFormateur_ShouldCallRepositoryDelete() {
        // Arrange
        Long id = 1L;

        // Act
        formateurService.deleteFormateur(id);

        // Assert
        verify(formateurRepository).deleteById(id);
    }
} 