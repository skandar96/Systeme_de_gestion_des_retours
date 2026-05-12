package com.example.project.Dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RetourProduitDto {
    private Long id;
    private Long produitId;
    private Long clientId;
    private String raison;
    private String etatTraitement;
    private LocalDateTime date;
    private List<Long> nonConformiteIds;
}
