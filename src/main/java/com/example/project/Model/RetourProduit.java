package com.example.project.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class RetourProduit {



	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur client;

    @Column(nullable = false)
    private String raison;

    @Column(nullable = false)
    private String etatTraitement;

    @Column(nullable = false)
    private LocalDateTime date;
    
    @OneToMany(mappedBy = "retourProduit")
    private List<NonConformite> nonConformite;

    
    
    
    
    
    
}