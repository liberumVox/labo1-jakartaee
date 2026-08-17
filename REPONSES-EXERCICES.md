# Laboratoire 1 — Réponses aux exercices

**Cours :** 420-140-GG Programmation Web Serveur III
**Étudiant :** Éric

---

## Exercice 2.1 — Cartographier l'écosystème

| Java SE | Jakarta EE |
|---|---|
| `ArrayList` | `@Entity` |
| `javac` | `Servlet` |
| `Thread` | `JAX-RS` |
| `Stream` | `JPA` |
| `HashMap` | `CDI` |

**5 / 5 — conforme au point de vérification du laboratoire.**

**Le critère de tri :** Java SE fournit le langage, la JVM et les bibliothèques de base — tout ce qui est utilisable sans serveur d'application. Jakarta EE est un ensemble de *spécifications* (des contrats, pas du code exécutable) destinées aux applications d'entreprise ; il faut un serveur ou un conteneur pour les exécuter.

- `ArrayList`, `HashMap` → `java.util`, API Collections de Java SE
- `javac` → compilateur livré avec le JDK
- `Thread` → `java.lang`, concurrence Java SE
- `Stream` → `java.util.stream`, ajouté en Java 8
- `@Entity` → annotation JPA (`jakarta.persistence`)
- `Servlet` → spécification Jakarta Servlet (`jakarta.servlet`)
- `JAX-RS` → spécification Jakarta RESTful Web Services
- `JPA` → spécification Jakarta Persistence
- `CDI` → spécification Jakarta Contexts and Dependency Injection

---

## Section 3 — Namespace `jakarta.*`

**Pourquoi le changement ?** Oracle a transféré la plateforme Java EE à la fondation Eclipse en 2017, mais a conservé la marque déposée « Java ». La fondation a donc dû renommer la plateforme en Jakarta EE, puis renommer les packages `javax.*` en `jakarta.*` à partir de Jakarta EE 9 (2020). **La logique du code ne change pas** : seul le nom des packages est renommé.

```java
// AVANT — javax.*
import javax.servlet.http.HttpServlet;
import javax.persistence.Entity;

// APRÈS — jakarta.*
import jakarta.servlet.http.HttpServlet;
import jakarta.persistence.Entity;
```

**Précision importante sur les versions de Tomcat** (vérifiée sur la matrice officielle Apache) :

| Tomcat | Servlet | Plateforme | Statut |
|---|---|---|---|
| 9.0.x | 4.0 | Java EE 8 (`javax.*`) | supporté |
| 10.0.x | 5.0 | Jakarta EE **9** (`jakarta.*`) | **fin de vie depuis 2022** |
| 10.1.x | 6.0 | Jakarta EE **10** (`jakarta.*`) | supporté |
| 11.0.x | 6.1 | Jakarta EE **11** (`jakarta.*`) | supporté |

Le laboratoire dit « Tomcat 10+ », mais comme le `pom.xml` de l'exercice 6.1 impose `jakarta.jakartaee-api` **10.0.0**, il faut précisément **Tomcat 10.1.x** (ou 11.0.x). Tomcat 10.0.x n'implémente que Jakarta EE 9 et provoquerait des erreurs à l'exécution.

---

## Mini-exercice 4.1 — Annotations

Fichier : `src/main/java/com/eric/labo1/model/Livre.java`

```java
@Entity
@Table(name = "livres")
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 100, nullable = false)
    private String titre;
    // ...
}
```

Une annotation n'exécute rien par elle-même : c'est une **métadonnée**. Le framework (ici un fournisseur JPA comme Hibernate) la lit par réflexion et agit en conséquence — créer la table, générer la clé, valider le champ.

---

## Mini-exercice 4.2 — Généricité

```java
public static <T> List<T> dernierElements(List<T> source, int n) { ... }
```

Le type est vérifié **à la compilation**, ce qui élimine le transtypage manuel et le risque de `ClassCastException` à l'exécution.

```java
// Sans generics — cast manuel, erreur possible à l'exécution
List repas = new ArrayList();
String r = (String) repas.get(0);

// Avec generics — sûr, vérifié à la compilation
List<String> repas = new ArrayList<>();
String r = repas.get(0);
```

---

## Mini-exercice 4.3 — `Map<String, Integer>`

```java
Map<String, Integer> prix = new LinkedHashMap<>();
prix.put("Pomme", 2);
prix.put("Banane", 3);
prix.put("Cerise", 8);
prix.forEach((fruit, p) -> System.out.println(fruit + " -> " + p + " $"));
```

**Sortie :**

```
Pomme -> 2 $
Banane -> 3 $
Cerise -> 8 $
```

`LinkedHashMap` a été choisi plutôt que `HashMap` parce qu'il conserve l'ordre d'insertion lors du parcours ; `HashMap` ne garantit aucun ordre.

---

## Mini-exercice 4.4 — Lambdas et Streams

```java
int somme = nombres.stream()
        .filter(n -> n % 2 == 0)   // ne garder que les pairs
        .map(n -> n * 2)           // multiplier par 2
        .reduce(0, Integer::sum);  // additionner
```

Avec `List.of(1..10)` : pairs `2,4,6,8,10` → doublés `4,8,12,16,20` → **somme = 60**.

Résultat vérifié par un test unitaire (`RappelsJavaSETest.streamFiltreDoubleEtAdditionne`).

---

## Mini-exercice 4.5 — Fichiers (`java.nio.file`)

```java
Files.write(fichier, lignes, StandardCharsets.UTF_8);
List<String> relu = Files.readAllLines(fichier, StandardCharsets.UTF_8);
```

`java.nio.file` (`Path`, `Files`) est l'API moderne recommandée : chemins typés, lecture/écriture en une ligne, meilleure gestion des erreurs et de l'encodage que `java.io` (`File`, `FileReader`, `BufferedReader`).

---

## Exercice 6.1 — Dépendances du `pom.xml`

| Dépendance | Scope | Pourquoi |
|---|---|---|
| `jakarta.platform:jakarta.jakartaee-api` | **`provided`** | L'API est déjà fournie par Tomcat à l'exécution. L'embarquer dans le `.war` créerait des conflits de chargement de classes (`ClassCastException`, `LinkageError`). |
| `org.postgresql:postgresql` | *(compile, par défaut)* | Tomcat ne fournit **aucun** pilote JDBC. Il doit donc être embarqué dans `WEB-INF/lib/` du `.war`. |

Aucune dépendance n'utilise `LATEST` ou `RELEASE` : toutes les versions sont figées dans le bloc `<properties>`, ce qui rend le build reproductible d'un poste à l'autre.

**Vérification :**

```bash
mvn dependency:tree
```

---

## Section 7 — Git

Trois commits documentés selon la convention du laboratoire :

```bash
git commit -m "feat: structure initiale du projet Maven Jakarta EE"
git commit -m "feat: servlet Bonjour et rappels Java SE (annotations, generics, streams, fichiers)"
git commit -m "docs: reponses aux exercices et README de deploiement"
```

| Préfixe | Usage |
|---|---|
| `feat:` | ajout d'une fonctionnalité |
| `fix:` | correction d'un bug |
| `docs:` | documentation uniquement |
| `refactor:` | changement de structure sans changement de comportement |

---

## Sources

- [Apache Tomcat — Which Version Do I Want ?](https://tomcat.apache.org/whichversion.html)
- [Apache Tomcat — Migration Guide 10.1.x](https://tomcat.apache.org/migration-10.1.html)
- [Jakarta EE — documentation officielle](https://jakarta.ee)
- [Maven Central — versions des artefacts](https://repo1.maven.org/maven2/)
