# Labo 1 — Jakarta EE

Projet Maven du laboratoire 1 de **420-140-GG Programmation Web Serveur III**.

## Prérequis

| Outil | Version |
|---|---|
| JDK | 25 ou supérieur |
| Apache Tomcat | **10.1.x** ou 11.0.x (pas 10.0.x — voir note plus bas) |
| Maven | 3.9+ |
| SGBD | PostgreSQL (ou MySQL, voir `pom.xml`) |

> **Note sur Tomcat :** le laboratoire dit « Tomcat 10+ », mais `jakarta.jakartaee-api:10.0.0`
> correspond à Jakarta EE 10 = **Servlet 6.0 = Tomcat 10.1.x**. Tomcat 10.0.x n'implémente
> que Jakarta EE 9 (Servlet 5.0) et est en fin de vie depuis 2022.

## Structure

```
labo1-jakartaee/
├── pom.xml
├── .gitignore
├── README.md
├── REPONSES-EXERCICES.md          réponses aux exercices 2.1, 4.x et 6.1
└── src/
    ├── main/
    │   ├── java/com/eric/labo1/
    │   │   ├── web/BonjourServlet.java       servlet « Bonjour, Jakarta EE ! »
    │   │   ├── model/Livre.java              annotations JPA (mini-ex. 4.1)
    │   │   └── rappels/RappelsJavaSE.java    generics, Map, Streams, fichiers (4.2 à 4.5)
    │   ├── resources/
    │   └── webapp/
    │       ├── index.jsp
    │       └── WEB-INF/web.xml
    └── test/java/com/eric/labo1/
        └── RappelsJavaSETest.java
```

## Construire

```bash
mvn clean package
```

Produit `target/labo1.war`.

## Déployer sur Tomcat

**Option A — copie manuelle**

```bash
cp target/labo1.war $CATALINA_HOME/webapps/
$CATALINA_HOME/bin/startup.sh      # startup.bat sous Windows
```

**Option B — depuis IntelliJ IDEA Ultimate**
`Run > Edit Configurations… > + > Tomcat Server > Local`, puis onglet *Deployment* → `labo1:war exploded`.

## Vérifier

| URL | Attendu |
|---|---|
| http://localhost:8080/labo1/ | page d'accueil avec le lien vers la servlet |
| http://localhost:8080/labo1/bonjour | **Bonjour, Jakarta EE !** |

C'est la page `/bonjour` qu'il faut capturer pour le livrable « capture d'écran ».

## Exécuter les rappels Java SE en console

```bash
mvn compile
java -cp target/classes com.eric.labo1.rappels.RappelsJavaSE
```

## Tests

```bash
mvn test
```

## Initialiser le dépôt Git

```bash
git init
git add .
git commit -m "feat: structure initiale du projet Maven Jakarta EE"
git branch -M main
git remote add origin <url-de-ton-depot>
git push -u origin main
```

## Dépannage

| Symptôme | Cause / solution |
|---|---|
| `ClassNotFoundException: jakarta.servlet.*` | Tomcat en version 9 ou antérieure. Passer à 10.1+. |
| `HTTP 404` sur `/labo1/bonjour` | Le contexte suit `<finalName>` du `pom.xml` (`labo1`). Vérifier le nom du `.war` dans `webapps/`. |
| Port 8080 occupé | Changer le port dans `conf/server.xml` ou arrêter le processus qui l'occupe. |
| Le `.war` ne se déploie pas | Lire `logs/catalina.out` (ou `catalina.<date>.log`) dans le dossier Tomcat. |
| `mvn: command not found` | Maven absent du `PATH`. |
| Erreur `release version 25 not supported` | Le JDK utilisé par Maven est < 25. Vérifier `mvn -version` et `JAVA_HOME`. |
