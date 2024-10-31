package com.Formation.formationapi.Modele.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

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
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    @Column(nullable = false)
    private String nom;
    
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    @Column(nullable = false)
    private String prenom;
    
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Column(nullable = false, unique = true)
    private String email;
    
    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    @Pattern(regexp = "^[0-9]{10}$", message = "Le numéro de téléphone doit contenir 10 chiffres")
    @Column(nullable = false)
    private String telephone;
    
    @NotBlank(message = "La spécialité est obligatoire")
    @Size(min = 2, max = 100, message = "La spécialité doit contenir entre 2 et 100 caractères")
    @Column(nullable = false)
    private String specialite;
    
    @OneToOne(mappedBy = "formateur")
    private Classe classe;
}
