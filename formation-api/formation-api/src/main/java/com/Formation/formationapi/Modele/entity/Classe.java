package com.Formation.formationapi.Modele.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "classes")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom de la classe est obligatoire")
    @Size(min = 2, max = 50)
    @Column(nullable = false)
    private String nom;
    
    @NotBlank(message = "Le numéro de salle est obligatoire")
    @Size(min = 1, max = 10)
    @Column(nullable = false)
    private String numSalle;
    
    @JsonIgnoreProperties({"classe", "formation"})
    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL)
    private List<Apprenant> apprenants = new ArrayList<>();
    
    @ManyToOne
    @JsonIgnoreProperties({"classes", "formations", "apprenants"})
    @JoinColumn(name = "formateur_id", nullable = true)
    private Formateur formateur;
}
