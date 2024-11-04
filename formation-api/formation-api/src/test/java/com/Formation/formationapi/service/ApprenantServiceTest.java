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

import com.Formation.formationapi.Modele.entity.Apprenant;
import com.Formation.formationapi.Repositories.ApprenantRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApprenantServiceTest {

    @Mock
    private ApprenantRepository apprenantRepository;

    @InjectMocks
    private ApprenantService apprenantService;

    private Apprenant apprenant;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        apprenant = new Apprenant();
        apprenant.setId(1L);
        apprenant.setNom("Martin");
        apprenant.setPrenom("Paul");
        apprenant.setEmail("paul.martin@test.com");
        apprenant.setNiveau("Débutant");
        
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void getAllApprenants_ShouldReturnPageOfApprenants() {
        // Arrange
        Page<Apprenant> expectedPage = new PageImpl<>(Arrays.asList(apprenant));
        when(apprenantRepository.findAll(pageable)).thenReturn(expectedPage);

        // Act
        Page<Apprenant> actualPage = apprenantService.getAllApprenants(pageable);

        // Assert
        assertEquals(expectedPage, actualPage);
        verify(apprenantRepository).findAll(pageable);
    }

    @Test
    void getApprenantById_ShouldReturnApprenant() {
        // Arrange
        when(apprenantRepository.findById(1L)).thenReturn(Optional.of(apprenant));

        // Act
        Optional<Apprenant> result = apprenantService.getApprenantById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(apprenant, result.get());
        verify(apprenantRepository).findById(1L);
    }

    @Test
    void getApprenantByEmail_ShouldReturnListOfApprenants() {
        // Arrange
        List<Apprenant> expectedApprenants = Arrays.asList(apprenant);
        when(apprenantRepository.findByEmail(anyString())).thenReturn(expectedApprenants);

        // Act
        List<Apprenant> actualApprenants = apprenantService.getApprenantByEmail("paul.martin@test.com");

        // Assert
        assertEquals(expectedApprenants, actualApprenants);
        verify(apprenantRepository).findByEmail("paul.martin@test.com");
    }

    @Test
    void createApprenant_ShouldReturnSavedApprenant() {
        // Arrange
        when(apprenantRepository.save(any(Apprenant.class))).thenReturn(apprenant);

        // Act
        Apprenant savedApprenant = apprenantService.createApprenant(apprenant);

        // Assert
        assertNotNull(savedApprenant);
        assertEquals(apprenant, savedApprenant);
        verify(apprenantRepository).save(apprenant);
    }

    @Test
    void updateApprenant_ShouldReturnUpdatedApprenant() {
        // Arrange
        when(apprenantRepository.save(any(Apprenant.class))).thenReturn(apprenant);

        // Act
        Apprenant updatedApprenant = apprenantService.updateApprenant(apprenant);

        // Assert
        assertNotNull(updatedApprenant);
        assertEquals(apprenant, updatedApprenant);
        verify(apprenantRepository).save(apprenant);
    }

    @Test
    void getApprenantByNom_ShouldReturnListOfApprenants() {

        List<Apprenant> expectedApprenants = Arrays.asList(apprenant);
        when(apprenantRepository.findByNom(anyString())).thenReturn(expectedApprenants);

        List<Apprenant> actualApprenants = apprenantService.getApprenantByNom("Martin");

        assertEquals(expectedApprenants, actualApprenants);
        verify(apprenantRepository).findByNom("Martin");
    }

    @Test
    void deleteApprenant_ShouldCallRepositoryDelete() {
        // Arrange
        Long id = 1L;

        // Act
        apprenantService.deleteApprenant(id);

        // Assert
        verify(apprenantRepository).deleteById(id);
    }
} 