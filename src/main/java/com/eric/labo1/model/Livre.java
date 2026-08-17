package com.eric.labo1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * Mini-exercice 4.1 (Annotations).
 *
 * Une annotation n'execute rien par elle-meme : elle ajoute une metadonnee que
 * le framework lit a l'execution (ici un fournisseur JPA comme Hibernate) pour
 * mapper la classe sur une table.
 *
 * - @Entity          : la classe est une entite persistante
 * - @Table           : nom de la table cible
 * - @Id              : cle primaire
 * - @GeneratedValue  : valeur generee par la base (auto-increment / identity)
 * - @NotNull         : contrainte de validation (Bean Validation)
 * - @Column          : contraintes de colonne (longueur, nullabilite)
 */
@Entity
@Table(name = "livres")
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 100, nullable = false)
    private String titre;

    protected Livre() {
        // constructeur sans argument requis par JPA
    }

    public Livre(String titre) {
        this.titre = titre;
    }

    public Long getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Override
    public String toString() {
        return "Livre{id=" + id + ", titre='" + titre + "'}";
    }
}
