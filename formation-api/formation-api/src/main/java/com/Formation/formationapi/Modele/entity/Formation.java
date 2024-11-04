package com.Formation.formationapi.Modele.entity;

import lombok.*;
import javax.validation.constraints.*;
import javax.persistence.*;

import com.Formation.formationapi.Modele.Enum.StatutFormation;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "formations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 3, max = 100)
    @Column(nullable = false)
    private String titre;
    
    @NotBlank(message = "Le niveau est obligatoire")
    @Column(nullable = false)
    private String niveau;
    
    @NotBlank(message = "Le prérequis est obligatoire")
    @Column(nullable = false)
    private String prerequis;
    
    @Min(value = 1)
    @Column(nullable = false)
    private int capaciteMin;
    
    @Min(value = 1)
    @Column(nullable = false)
    private int capaciteMax;
    
    @NotNull(message = "La date de début est obligatoire")
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateDebut;
    
    @NotNull(message = "La date de fin est obligatoire")
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateFin;
    
    
    @ManyToOne
    @JoinColumn(name = "formateur_id")
    @JsonIgnoreProperties({"formations", "classes", "apprenants"})
    private Formateur formateur;
    
    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"formation", "classe"})
    private List<Apprenant> apprenants = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutFormation statut;
}
