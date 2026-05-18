package com.example.project.Dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RetourProduitDto {
    private Long id;
    private Long produitId;
    private String produitNom;
    private String clientname;
    private Long clientId;
    private String raison;
    private String etatTraitement;
    private LocalDateTime date;
    private Long quantite;
    
}
