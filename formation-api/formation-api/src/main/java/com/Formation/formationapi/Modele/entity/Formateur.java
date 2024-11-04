package com.Formation.formationapi.Modele.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "formateurs")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Formateur {
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
    
    @NotBlank(message = "La spécialité est obligatoire")
    @Column(nullable = false)
    private String specialite;
    
    @JsonIgnoreProperties({"formateur", "classe"})
    @OneToMany(mappedBy = "formateur")
    private List<Formation> formations = new ArrayList<>();
    
    @JsonIgnoreProperties({"formateur", "formation"})
    @OneToMany(mappedBy = "formateur")
    private List<Classe> classes = new ArrayList<>();
}
