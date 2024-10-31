package com.Formation.Modele.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.Formation.Modele.Enum.StatutFormation;
import javax.persistence.*;
import javax.validation.constraints.*;


@Entity
@Table(name = "formations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom de la formation est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    @Column(nullable = false)
    private String nom;
    
    @NotBlank(message = "La description est obligatoire")
    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
    @Column(nullable = false)
    private String description;
    
    @NotNull(message = "La date de début est obligatoire")
    @Future(message = "La date de début doit être dans le futur")
    @Column(nullable = false)
    private Date dateDebut;
    
    @NotNull(message = "La date de fin est obligatoire")
    @Future(message = "La date de fin doit être dans le futur")
    @Column(nullable = false)
    private Date dateFin;
    
    @Enumerated(EnumType.STRING)
    private StatutFormation statut;
    @OneToMany(mappedBy = "formation")
    private List<Classe> classes;
    
    public enum StatutFormation {
        PLANIFIEE, EN_COURS, TERMINEE, ANNULEE
    }
    
    public List<Formateur> getFormateurs() {
        return classes == null ? Collections.emptyList() :
            classes.stream()
                  .filter(classe -> classe != null && classe.getFormateur() != null)
                  .map(Classe::getFormateur)
                  .distinct()
                  .collect(Collectors.toList());
    }
    
    public List<Apprenant> getAllApprenants() {
        return classes.stream()
                     .<Apprenant>flatMap(classe -> classe.getApprenants().stream())
                     .collect(Collectors.toList());
    }
}
