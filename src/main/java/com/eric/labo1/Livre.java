package com.eric.labo1;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

// Mini-exercice 4.1 : annotations
@Entity
@Table(name = "livres")
public class Livre {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(length = 100)
    private String titre;

    public Livre() {
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
}
