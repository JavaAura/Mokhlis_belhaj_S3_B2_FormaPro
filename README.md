# Application de Gestion de Formation

## Description du projet
Cette application est une API REST de gestion de formation permettant de gérer les formations, les formateurs, les apprenants et les classes. Elle facilite l'organisation et le suivi des formations au sein d'un établissement éducatif.

## Objectif général
L'objectif principal est de fournir une plateforme centralisée pour :
- Gérer les formations et leur contenu
- Administrer les formateurs et leurs attributions
- Suivre les apprenants et leur progression
- Organiser les classes et les sessions de formation

## Technologies utilisées
- Java 8
- Spring Boot 2.7.18
- Spring Data JPA
- H2/PostgreSQL
- Maven 3.9.9
- Swagger/OpenAPI 1.6.15
- Spring Validation
- Lombok
- JUnit 5.8.2
- Mockito 3.11.2
- JaCoCo 0.8.7

## Structure du projet
```
formation-api/
├── src/
│   ├── main/
│   │   ├── java/ com/Formation/formationapi/
│   │   │       ├── FormationApiApplication.java  
│   │   │       ├── Configuration/
│   │   │       │   └── SwaggerConfig.java
│   │   │       ├── Controleur/
│   │   │       │   ├── ApprenantController.java
│   │   │       │   ├── ClasseController.java
│   │   │       │   ├── FormateurController.java
│   │   │       │   └── FormationController.java
│   │   │       ├── Modele/
│   │   │       │   ├── entity/
│   │   │       │   │   ├── Apprenant.java
│   │   │       │   │   ├── Classe.java
│   │   │       │   │   ├── Formateur.java
│   │   │       │   │   └── Formation.java
│   │   │       │   └── Enum/
│   │   │       │       └── StatutFormation.java
│   │   │       ├── Repositories/
│   │   │       │   ├── ApprenantRepository.java
│   │   │       │   ├── ClasseRepository.java
│   │   │       │   ├── FormateurRepository.java
│   │   │       │   └── FormationRepository.java
│   │   │       ├── service/
│   │   │       │   ├── ApprenantService.java
│   │   │       │   ├── ClasseService.java
│   │   │       │   ├── FormateurService.java
│   │   │       │   └── FormationService.java
│   │   │       └── Exceptions/
│   │   │           ├── ErrorResponse.java     
│   │   │           ├── GlobalExceptionHandler.java  
│   │   │           ├── ResourceNotFoundException.java
│   │   │           └── ValidationException.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       ├── application-prod.properties
│   │       ├── application-secrets.properties
│   │       └── logback-spring.xml
│   └── test/
│       └── java/ com/Formation/formationapi/
│              ├── FormationApiApplicationTests.java
│              ├── integration/
│              │   ├── ApprenantIntegrationTest.java
│              │   ├── ClasseIntegrationTest.java
│              │   ├── FormateurIntegrationTest.java
│              │   └── FormationIntegrationTest.java
│              └── service/
│                  ├── ApprenantServiceTest.java
│                  ├── ClasseServiceTest.java
│                  ├── FormateurServiceTest.java
│                   └── FormationServiceTest.java
|
├── pom.xml
└── README.md
```	



## Architecture
L'application suit une architecture en couches (Layer Architecture) :
- Couche Controller : Gestion des requêtes HTTP
- Couche Service : Logique métier
- Couche Repositories : Accès aux données
- Couche Model : Entités et Enum
- couche Exceptions : Gestion des exceptions


## Installation et utilisation

### Prérequis
- JDK 8 
- Maven 3.9.9
- PostgreSQL 
- IDE (Eclipse)

### Configuration de la base de données
1. Créer une base de données PostgreSQL
2. changer le profil dans le fichier `application.properties`
```
spring.profiles.active=dev

```	
remplacer dev par prod  pour utiliser la base de données postgres

ajouter un fichier `application-secrets.properties` et ajouter les variables d'environnement pour utiliser 
exemple:
```
db.username="your_username"
db.password="your_password"
```	
change name of base de données dans le fichier `application-prod.properties`
```
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
```	

### Lancement de l'application
1. Cloner le repository
```bash
git https://github.com/JavaAura/Mokhlis_belhaj_S3_B2_FormaPro.git
```
2. Naviguer jusqu'au dossier formation-api
3. créer le package jar
```bash
mvn clean package
```
4. lancer l'application
```bash
java -jar target/formation-api-0.0.1-SNAPSHOT.jar
```

## Documentation API
La documentation de l'API est disponible via Swagger UI à l'adresse :
[http://localhost:8085/swagger-ui/index.html](http://localhost:8085/swagger-ui/index.html)

### Améliorations possibles

- ajouter un systeme de authentification
- ajoutes un systeme de gestion des permissions et des rôles
- ajoutes un systeme de gestion des absences
- ajoutes un systeme de notification par email
- ajoutes un systeme de examens et de notes

## Auteurs
- Mokhlis Belhaj
- github : [JavaAura](https://github.com/BelhajMokhlis)
- linkedin : [Mokhlis Belhaj](https://www.linkedin.com/in/mokhlis-belhaj/)
- Jira : [Mokhlis Belhaj](https://belhajmokhlis.atlassian.net/jira/software/projects/FOR/boards/171)




