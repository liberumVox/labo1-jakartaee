package com.eric.labo1;

import com.eric.labo1.model.Livre;
import com.eric.labo1.rappels.RappelsJavaSE;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RappelsJavaSETest {

    @Test
    void genericiteRetourneLesDerniersElements() {
        List<String> plats = List.of("Pizza", "Poutine", "Sushi", "Tacos");
        assertEquals(List.of("Sushi", "Tacos"), RappelsJavaSE.dernierElements(plats, 2));
    }

    @Test
    void mapContientTroisFruits() {
        Map<String, Integer> prix = RappelsJavaSE.prixDesFruits();
        assertEquals(3, prix.size());
        assertEquals(2, prix.get("Pomme"));
    }

    @Test
    void streamFiltreDoubleEtAdditionne() {
        List<Integer> nombres = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        // pairs : 2,4,6,8,10 -> doubles : 4,8,12,16,20 -> somme : 60
        assertEquals(60, RappelsJavaSE.sommeDesPairsDoubles(nombres));
        assertEquals(List.of(4, 8, 12, 16, 20), RappelsJavaSE.pairsDoubles(nombres));
    }

    @Test
    void fichierEcritPuisRelu() throws IOException {
        Path tmp = Files.createTempFile("labo1", ".properties");
        List<String> lignes = List.of("cle=valeur", "autre=42");
        assertEquals(lignes, RappelsJavaSE.ecrireEtRelire(tmp, lignes));
        Files.deleteIfExists(tmp);
    }

    @Test
    void livreExposeSonTitre() {
        Livre livre = new Livre("Effective Java");
        assertEquals("Effective Java", livre.getTitre());
        assertTrue(livre.toString().contains("Effective Java"));
    }
}
