package com.eric.labo1.rappels;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Livrable 3 : classe illustrant les notions de la section 4 du laboratoire.
 * Couvre les mini-exercices 4.2, 4.3, 4.4 et 4.5.
 *
 * Executable en ligne de commande :
 *   mvn compile
 *   java -cp target/classes com.eric.labo1.rappels.RappelsJavaSE
 */
public final class RappelsJavaSE {

    private RappelsJavaSE() {
        // classe utilitaire : pas d'instanciation
    }

    // ------------------------------------------------------------------
    // 4.2 Genericite
    // ------------------------------------------------------------------

    /**
     * Methode generique : le type est parametre et verifie a la COMPILATION,
     * donc aucun cast manuel n'est necessaire a l'execution.
     *
     * Sans generics : List repas = new ArrayList(); String r = (String) repas.get(0);
     * Avec generics : List<String> repas = new ArrayList<>(); String r = repas.get(0);
     */
    public static <T> List<T> dernierElements(List<T> source, int n) {
        int debut = Math.max(0, source.size() - n);
        return new ArrayList<>(source.subList(debut, source.size()));
    }

    // ------------------------------------------------------------------
    // 4.3 Structures de donnees : Map
    // ------------------------------------------------------------------

    /**
     * Mini-exercice 4.3 : Map<String, Integer> associant trois fruits a leur prix.
     * LinkedHashMap conserve l'ordre d'insertion (HashMap ne garantit aucun ordre).
     */
    public static Map<String, Integer> prixDesFruits() {
        Map<String, Integer> prix = new LinkedHashMap<>();
        prix.put("Pomme", 2);
        prix.put("Banane", 3);
        prix.put("Cerise", 8);
        return prix;
    }

    // ------------------------------------------------------------------
    // 4.4 Lambdas et Streams
    // ------------------------------------------------------------------

    /**
     * Mini-exercice 4.4 : ne garder que les nombres pairs, les multiplier par 2,
     * puis retourner leur somme via reduce().
     */
    public static int sommeDesPairsDoubles(List<Integer> nombres) {
        return nombres.stream()
                .filter(n -> n % 2 == 0)   // lambda : predicat
                .map(n -> n * 2)           // lambda : transformation
                .reduce(0, Integer::sum);  // reference de methode : agregation
    }

    /** Variante : materialise la liste transformee au lieu de l'agreger. */
    public static List<Integer> pairsDoubles(List<Integer> nombres) {
        return nombres.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * 2)
                .sorted()
                .collect(Collectors.toList());
    }

    // ------------------------------------------------------------------
    // 4.5 Fichiers : java.nio.file
    // ------------------------------------------------------------------

    /**
     * Mini-exercice 4.5 : ecriture puis lecture d'un fichier avec l'API moderne
     * java.nio.file (Path / Files), qui remplace avantageusement java.io.
     */
    public static List<String> ecrireEtRelire(Path fichier, List<String> lignes) throws IOException {
        Files.write(fichier, lignes, StandardCharsets.UTF_8);
        return Files.readAllLines(fichier, StandardCharsets.UTF_8);
    }

    // ------------------------------------------------------------------

    public static void main(String[] args) throws IOException {

        System.out.println("=== 4.2 Genericite ===");
        List<String> plats = List.of("Pizza", "Poutine", "Sushi", "Tacos");
        System.out.println("2 derniers plats : " + dernierElements(plats, 2));

        System.out.println("\n=== 4.3 Map<String, Integer> ===");
        prixDesFruits().forEach((fruit, prix) -> System.out.println(fruit + " -> " + prix + " $"));

        System.out.println("\n=== 4.4 Lambdas et Streams ===");
        List<Integer> nombres = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println("Pairs doubles : " + pairsDoubles(nombres));
        System.out.println("Somme (reduce) : " + sommeDesPairsDoubles(nombres));

        System.out.println("\n=== 4.5 Fichiers (java.nio.file) ===");
        Path fichier = Path.of("config.properties");
        List<String> relu = ecrireEtRelire(fichier, List.of(
                "db.url=jdbc:postgresql://localhost:5432/labo1",
                "db.user=eric"
        ));
        relu.forEach(System.out::println);
        Files.deleteIfExists(fichier);

        System.out.println("\n=== 4.1 Annotations ===");
        System.out.println("Voir com.eric.labo1.model.Livre (@Entity, @Id, @GeneratedValue, @NotNull, @Column)");
    }
}
