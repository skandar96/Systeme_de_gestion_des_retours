package com.example.project.Dto;

import com.example.project.Model.Gravite;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NonConformiteDto {
    private Long id;
    private String description;
    private Gravite gravite;
    private LocalDate date;
    private Long retourProduitId;
}
