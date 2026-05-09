package com.example.project.Dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProduitRequest {
    @NotBlank
    @Size(max = 150)
    private String nom;

    @Size(max = 1000)
    private String description;

    @NotNull
    @PositiveOrZero
    private Double prix;

    @NotNull
    @Min(0)
    private Integer quantite;
}