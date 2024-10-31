package com.Formation.formationapi.Modele.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.*;


import java.util.Collection;
import java.util.List;
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
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    @Column(nullable = false)
    private String nom;
    
    @NotBlank(message = "Le numéro de salle est obligatoire")
    @Size(min = 1, max = 10, message = "Le numéro de salle doit contenir entre 1 et 10 caractères")
    @Column(nullable = false)
    private String numSalle;
    
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "formation_id", nullable = true)
    private Formation formation;
    
   
    @OneToOne(optional = true)
    @JoinColumn(name = "formateur_id", nullable = true)
    private Formateur formateur;
    
    // list of apprenants can be null
    @OneToMany(mappedBy = "classe")
    private List<Apprenant> apprenants;

	public Formateur getFormateur() {
        return formateur;
        }

	public Collection<Apprenant> getApprenants() {
		return apprenants;
	}
    
}
