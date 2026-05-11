package com.example.project.Model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "produits")
@Getter
@Setter
@NoArgsConstructor
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotBlank
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String nom;

    @Size(max = 1000)
    @Column(length = 1000)
    private String description;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Double prix;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer quantite;
    
    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<RetourProduit> retours;
}