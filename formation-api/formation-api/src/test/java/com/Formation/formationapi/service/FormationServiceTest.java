package com.Formation.formationapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Formation.formationapi.Modele.Enum.StatutFormation;
import com.Formation.formationapi.Modele.entity.Formation;
import com.Formation.formationapi.Repositories.FormationRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FormationServiceTest {

    @Mock
    private FormationRepository formationRepository;

    @InjectMocks
    private FormationService formationService;

    private Formation formation;

    @BeforeEach
    void setUp() {
        formation = new Formation();
        formation.setId(1L);
        formation.setTitre("Formation Test");
        formation.setNiveau("Intermédiaire");
        formation.setPrerequis("Java Basics");
        formation.setCapaciteMin(5);
        formation.setCapaciteMax(15);
        formation.setDateDebut(new Date());
        formation.setDateFin(new Date());
        formation.setStatut(StatutFormation.PLANIFIEE);
    }

    @Test
    void getAllFormations_ShouldReturnListOfFormations() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Formation> expectedPage = mock(Page.class);
        when(formationRepository.findAll(pageable)).thenReturn(expectedPage);

        // Act
        Page<Formation> actualFormations = formationService.getAllFormations(pageable);

        // Assert
        assertEquals(expectedPage, actualFormations);
        verify(formationRepository).findAll(pageable);
    }

    @Test
    void getFormationById_ShouldReturnFormation() {
        // Arrange
        when(formationRepository.findById(1L)).thenReturn(Optional.of(formation));

        // Act
        Optional<Formation> result = formationService.getFormationById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(formation, result.get());
        verify(formationRepository).findById(1L);
    }

    @Test
    void saveFormation_ShouldReturnSavedFormation() {
        // Arrange
        when(formationRepository.save(any(Formation.class))).thenReturn(formation);

        // Act
        Formation savedFormation = formationService.saveFormation(formation);

        // Assert
        assertNotNull(savedFormation);
        assertEquals(formation, savedFormation);
        verify(formationRepository).save(formation);
    }

    @Test
    void getFormationsByDate_ShouldReturnListOfFormations() {
        // Arrange
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 86400000); // Add 1 day in milliseconds
        List<Formation> expectedFormations = Arrays.asList(formation);
        when(formationRepository.findByDateDebutBetween(startDate, endDate)).thenReturn(expectedFormations);

        // Act
        List<Formation> actualFormations = formationService.getFormationsByDate(startDate, endDate);

        // Assert
        assertEquals(expectedFormations, actualFormations);
        verify(formationRepository).findByDateDebutBetween(startDate, endDate);
    }

    @Test
    void deleteFormation_ShouldCallRepositoryDelete() {
        // Arrange
        Long id = 1L;

        // Act
        formationService.deleteFormation(id);

        // Assert
        verify(formationRepository).deleteById(id);
    }
} 