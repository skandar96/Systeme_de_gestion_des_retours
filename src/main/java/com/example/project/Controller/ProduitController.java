package com.example.project.Controller;

import com.example.project.Dto.ProduitRequest;
import com.example.project.Dto.ProduitResponse;
import com.example.project.Service.ProduitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProduitResponse create(@Valid @RequestBody ProduitRequest request) {
    	
        return produitService.create(request);
    }

    @GetMapping("/{id}")
    public ProduitResponse getById(@PathVariable Long id) {
        return produitService.getById(id);
    }

    @GetMapping
    public List<ProduitResponse> getAll() {
        return produitService.getAll();
    }

    @PutMapping("/{id}")
    public ProduitResponse update(@PathVariable Long id, @Valid @RequestBody ProduitRequest request) {
        return produitService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        produitService.delete(id);
    }
}