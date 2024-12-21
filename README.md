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

Utilisez Maven pour compiler et exécuter l'application.

```bash
mvn clean install
mvn spring-boot:run
```

### 3. Accédez à l'API :

- Par défaut, l'application est disponible sur le port 8080.
- Endpoint principal : http://localhost:8080/api/hello

---

## Lancer avec Docker

### 1. Construisez l'image Docker :

```bash
docker build -t teamoop-backend:latest .
```

### 2. Lancez le conteneur :

```bash
docker run -p 8080:8080 teamoop-backend:latest
```

## Ou utilisait l'image public
Si vous ne souhaitez pas construire l'image Docker localement, utilisez celle hébergée sur **GitHub Container Registry** :
```bash
docker pull ghcr.io/lenelson/teamoop-backend:latest
docker run -p 8080:8080 ghcr.io/lenelson/teamoop-backend:latest
```

### 3. Accédez à l'API :

http://localhost:8080/api/hello

---

## Swagger UI

- **Swagger UI** est disponible à l'adresse suivante : 
  - http://localhost:8080/swagger-ui/index.html
- Assurez-vous que l'application **Spring Boot** est en cours d'exécution.
- Endpoint des spécifications **OpenAPI** : 
  - http://localhost:8080/v3/api-docs

### Si l'interface rencontre un problème
```bash
mvn clean instal
mvn spring-boot:run
```

---
## Déploiement avec CI/CD
   - L'application est configurée pour utiliser **GitHub Actions** pour les étapes suivantes :
     - **Build** et **tests** Maven.
     - Génération et publication d'une image **Docker** sur **GitHub Container Registry**.

---

## Rapports de couverture
- Un rapport **JaCoCo** est généré automatiquement lors des builds **CI/CD**.
- Vous pouvez le télécharger depuis les artifacts du workflow **GitHub**.