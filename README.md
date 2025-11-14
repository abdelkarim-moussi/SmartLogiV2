# 🏷️ Smart Delivery Management System (SDMS)
📦 Contexte du projet

La société SmartLogi, spécialisée dans la livraison de colis à travers le Maroc, souhaite moderniser et automatiser la gestion de ses opérations logistiques.
Actuellement, les livraisons sont gérées manuellement via des fichiers Excel et des registres papier, entraînant des erreurs de saisie, des pertes de données, des retards et un manque de visibilité sur le suivi des colis.

Le SDMS (Smart Delivery Management System) a pour objectif de fournir une solution web complète permettant de :

Collecter les colis depuis les clients expéditeurs.

Stocker temporairement les colis dans les entrepôts avant expédition.

Planifier et assurer la livraison aux destinataires finaux.

Offrir une traçabilité complète et un historique des statuts.

Optimiser la planification logistique par zones et priorités.

Réduire les erreurs humaines et améliorer le suivi global.

🎯 Objectifs

Centraliser la gestion des clients expéditeurs, destinataires, colis, livreurs et zones de livraison.

Suivre le flux complet des colis : collecte → stockage → planification → livraison.

Permettre un suivi précis des colis selon plusieurs critères (ville, zone, statut, priorité…).

Fournir un historique complet de chaque colis.

Optimiser les tournées et réduire les retards grâce à une planification intelligente.

Faciliter la prise de décision pour les gestionnaires logistiques.

👥 Utilisateurs du système
Rôle	Description principale
Gestionnaire logistique	Supervise les opérations, planifie les tournées, gère les livreurs et le stock.
Livreur	Consulte ses colis assignés et met à jour leur statut.
Client expéditeur	Crée des demandes de livraison et suit l’état de ses colis.
Destinataire	Suit le statut des colis à recevoir.

🗃️ Modèle métier (Base de données)
Tables principales
Table	Description
ClientExpéditeur	id, nom, prénom, email, téléphone, adresse
Destinataire	id, nom, prénom, email, téléphone, adresse
Livreur	id, nom, prénom, téléphone, véhicule, zoneAssignée
Colis	id, description, poids, statut (créé, collecté, en stock, en transit, livré), priorité, idLivreur, idClientExpéditeur, idDestinataire, idZone, villeDestination
Zone	id, nom, codePostal
HistoriqueLivraison	id, idColis, statut, dateChangement, commentaire
Produit	id, nom, catégorie, poids, prix
Colis_Produit	idColis, idProduit, quantité, prix, dateAjout
🛠️ Exigences techniques
Technologies principales

Back-end : Spring Boot (API REST)

Base de données : PostgreSQL

ORM / Migrations : Liquibase

DTO / Mapping : MapStruct

Documentation API : Swagger / OpenAPI

Logs : SLF4J

Validation : @Valid, @NotNull, @Size, etc.

Emails (bonus) : SMTP

Configuration : YAML

Build & Dépendances : Maven

Versioning : Git / GitHub

Suivi projet : Jira

🧱 Architecture logicielle
Controller  →  Service  →  Repository  →  Database

Bonnes pratiques

Gestion centralisée des exceptions.

Respect des conventions Spring & Java (noms, packaging, lisibilité).

Utilisation de DTOs pour séparer la logique métier et la couche API.

Pagination & tri pour toutes les listes volumineuses.

Commentaires et logs clairs pour faciliter la maintenance.

🚀 Mise en place du projet
1️⃣ Prérequis

Java 17+

Maven 3.9+

PostgreSQL 14+

IDE : IntelliJ / Eclipse / VSCode

Liquibase

2️⃣ Cloner le projet
git clone https://github.com/abdelkarim-moussi/SmartLogiV2
cd SmartLogiV2

3️⃣ Configurer la base de données

Modifier application.properties :

spring.application.name=SmartLogiV2
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/smartLogiV2_db?createDatabaseIfNotExist=true&amp;allowPublicKeyRetrieval=true&amp;useSSL=false&amp;useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=UTC
spring.datasource.username= postgres
spring.datasource.password= moussi@25
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=none
spring.liquibase.change-log=classpath:/db/changelog/db.changelog-master.yaml
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.url=/api-docs

springdoc.swagger-ui.operations-sorter=method


4️⃣ Lancer l’application
mvn spring-boot:run

5️⃣ Accéder à la documentation API

Swagger : http://localhost:8080/swagger-ui.html

💡 Améliorations futures

Tableau de bord analytique (statistiques, cartes, graphiques).

Intégration GPS / Google Maps API pour suivi en temps réel.

Notifications SMS.

Application mobile (livreurs et clients).

Gestion des retours / annulations.

### 🧪 Qualité & Stratégie de Test

Afin de garantir la fiabilité, la stabilité et la performance du Smart Delivery Management System (SDMS), une stratégie de test complète a été mise en place couvrant l’ensemble des couches de l’application.

✔️ Stratégie de test

La stratégie adoptée repose sur plusieurs niveaux de validation :

1. Tests unitaires

Portent sur les services, utils et composants métier.

Vérifient la logique métier isolée.

Ciblent principalement les couches Service et Utils.

Réalisés avec : JUnit 5, Mockito.

2. Tests d’intégration

Portent sur l’interaction entre :

Controller ↔ Service

Service ↔ Repository

Repository ↔ Base de données

Tests exécutés avec une base embarquée ou un container (ex : Testcontainers PostgreSQL).

Vérifient la cohérence globale de l’application.

3. Tests API / End-to-End

Vérifient l’ensemble de la chaîne HTTP, de l’appel REST à la réponse.

Réalisés avec : Spring MockMvc, Postman, ou RestAssured.

Couvrent : création, mise à jour, recherche, pagination, filtrage.


🔧 Outils utilisés

JUnit 5 : framework de tests unitaires.

Mockito : création de mocks pour isoler la logique métier.

Spring Boot Test : tests d’intégration et API.

Testcontainers (H2) : tests d’intégration réalistes avec base isolée.

Jacoco : mesure de couverture du code.

sonarQube : mesure de couverture et analyse du code

Postman : collections de tests API.

📊 Indicateurs obtenus
Indicateur	Résultat
Couverture de tests (branches & lignes)	71.9% selon les modules
Maintainability A 76
Duplication 0.0%
Reliability A