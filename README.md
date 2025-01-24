# Teamoop Backend

API REST backend pour le projet Teamoop, développé avec Spring Boot. Cette application offre des outils collaboratifs pour gérer et rejoindre des projets.

---

## Guide pour lancer l'application
### 1. Prérequis
   - **Java 17+** installé sur votre machine.
   - **Maven** installé pour la gestion des dépendances.
   - **Docker** installé pour exécuter l'application dans un conteneur.

---

## Mise en route
### 1. Cloner le dépôt

```bash
git clone https://github.com/votre-repo/teamoop-backend.git
cd teamoop-backend
```

### 2. Construire et exécuter le projet

Utilisez docker pour compiler et éxécuter l'application

Si vous souhaitez developper sur le projet
```bash
docker compose -f docker-compose-dev.yml up -d
```
Lorsque des modifications sont effectués le code est automatiquement recompilé depuis docker en environ 20s.

> __Remarque :__ Si vous utilisez __IntelliJ IDEA__ comme outil de développement, après avoir ajouté la dépendance `devtools`,  
> activez les propriétés suivantes :
>
> 1. Allez dans __IntelliJ IDEA -> Préférences -> Build, Execution, Deployment -> Compiler__  
     > et activez l'option `Build project automatically`.
>
> 2. Allez dans __IntelliJ IDEA -> Préférences -> Paramètres avancés__  
     > et activez l'option `Allow auto-make to start even if developed application is currently running`.

Pour une utilisation si vous travaillez sur le projet frontend
```bash
docker compose up -d
```

### 3. Accédez à l'API :

- Par défaut, l'application est disponible sur le port 8080.
- Endpoint principal : http://localhost:8080/api/hello

## Tests

Exécution des tests unitaire et de composant :
```bash
 mvn test
```

Remarque : 

Si le lancement des tests échoue,veuiller à bien eteindre les containers docker du projet
## Swagger UI

- **Swagger UI** est disponible à l'adresse suivante : 
  - http://localhost:8080/swagger-ui/index.html
- Assurez-vous que l'application **Spring Boot** est en cours d'exécution.
- Endpoint des spécifications **OpenAPI** : 
  - http://localhost:8080/v3/api-docs

Si l'interface swagger rencontre un problème il faudra rebuild le conteneur docker

---
## Déploiement avec CI/CD
   - L'application est configurée pour utiliser **GitHub Actions** pour les étapes suivantes :
     - **Build** et **tests** Maven.
     - Génération et publication d'une image **Docker** sur **GitHub Container Registry**.

---

## Rapports de couverture
- Un rapport **JaCoCo** est généré automatiquement lors des builds **CI/CD**.
- Vous pouvez le télécharger depuis les artifacts du workflow **GitHub**.

---

## Gestion des dépendances avec Dependabot
Ce projet utilise Dependabot pour maintenir les dépendances à jour. 
Dependabot est un outil qui génère automatiquement des pull requests pour 
les mises à jour des dépendances déclarées dans le fichier `pom.xml`

### Configuration actuelle
Dependabot est configuré pour :

- Mettre à jour les dépendances définies dans le fichier `pom.xml`.
- Générer une pull request pour chaque mise à jour détectée.
- Vérifier les mises à jour sur une base hebdomadaire.

### Avant de fusionner une pull request de Dependabot

- Lisez la description pour comprendre les impacts de la mise à jour.
> ⚠️ Attention : Assurez-vous de vérifier les mises à jour critiques pour éviter des problèmes de compatibilité.