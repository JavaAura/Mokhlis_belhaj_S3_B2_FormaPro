package com.Formation.formationapi.Modele.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.Formation.formationapi.Modele.Enum.StatutFormation;
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


    // Getters and Setters
    public Long getId() {
        return id;
    }   
    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public StatutFormation getStatut() {
		return statut;
	}

	public void setStatut(StatutFormation statut) {
		this.statut = statut;
	}

	public List<Classe> getClasses() {
		return classes;
	}

	public void setClasses(List<Classe> classes) {
		this.classes = classes;
	}
    
}
