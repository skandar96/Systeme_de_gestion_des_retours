package com.example.project.Service;

import com.example.project.Dto.ProduitRequest;
import com.example.project.Dto.ProduitResponse;

import java.util.List;

public interface ProduitService {
    ProduitResponse create(ProduitRequest request);
    ProduitResponse getById(Long id);
    List<ProduitResponse> getAll();
    ProduitResponse update(Long id, ProduitRequest request);
    void delete(Long id);
}