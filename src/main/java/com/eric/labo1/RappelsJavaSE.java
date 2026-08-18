package com.eric.labo1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Cette classe illustre trois notions de la section 4 :
// les collections (Map), les lambdas/Streams et la manipulation de fichiers.
public class RappelsJavaSE {

    public static void main(String[] args) throws IOException {

        // --- 4.3 Map<String, Integer> ---
        System.out.println("1. Prix des fruits");
        Map<String, Integer> prix = new HashMap<>();
        prix.put("Pomme", 2);
        prix.put("Banane", 3);
        prix.put("Cerise", 8);

        for (Map.Entry<String, Integer> entree : prix.entrySet()) {
            System.out.println(entree.getKey() + " : " + entree.getValue() + " $");
        }
        System.out.println();

        // --- 4.4 Lambda et Stream ---
        System.out.println("2. Nombres pairs multiplies par 2");
        List<Integer> nombres = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            nombres.add(i);
        }

        int somme = nombres.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * 2)
                .reduce(0, (a, b) -> a + b);

        System.out.println("Somme : " + somme);
        System.out.println();

        // --- 4.5 Lecture d'un fichier avec java.nio ---
        System.out.println("3. Lecture d'un fichier");
        Path fichier = Path.of("config.properties");
        Files.writeString(fichier, "db.url=jdbc:postgresql://localhost:5432/labo1\ndb.user=eric\n");

        List<String> lignes = Files.readAllLines(fichier);
        lignes.forEach(System.out::println);

        Files.delete(fichier);
    }
}
