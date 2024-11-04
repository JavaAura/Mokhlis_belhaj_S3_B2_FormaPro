package com.Formation.formationapi.Modele.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "apprenants")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Apprenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50)
    @Column(nullable = false)
    private String nom;
    
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 50)
    @Column(nullable = false)
    private String prenom;
    
    @NotBlank(message = "L'email est obligatoire")
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    
    @NotBlank(message = "Le niveau est obligatoire")
    @Column(nullable = false)
    private String niveau;
    
   @ManyToOne
    @JoinColumn(name = "formation_id")
    @JsonIgnoreProperties({"apprenants","formateur"})
    private Formation formation;



    @ManyToOne
    @JoinColumn(name = "classe_id", nullable = true)
    @JsonIgnoreProperties({"apprenants","formation"})
    private Classe classe;
}
