package com.example.project.Controller;

import com.example.project.Dto.ProduitDto;
import com.example.project.Mapper.ProduitMapper;
import com.example.project.Model.Produit;
import com.example.project.Service.ProduitService;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    @Autowired
    private ProduitService produitService;
    @Autowired
    private ProduitMapper produitMapper;
    
    @GetMapping("/all")
    public ResponseEntity<List<ProduitDto>> gettallprod(){
    	List<Produit> produits = produitService.getAllProduits();
		List<ProduitDto> produitsDto = produitMapper.toDtos(produits);
		return ResponseEntity.ok(produitsDto);
    	
    	
    }
    
    
    
   
    @GetMapping("/get/{id}")
    
    
    
    public ResponseEntity<ProduitDto> getProduitById(@PathVariable Long id) {
        Produit p =produitService.getProduitById(id);
        if (p == null) {
			
			return ResponseEntity.status(404).body(null);
		}
        ProduitDto pDto = produitMapper.toDto(p);
        		return ResponseEntity.ok(pDto);
    }

    @PostMapping("/add")
    public ResponseEntity<Produit> addProduit(@Valid @RequestBody ProduitDto produitDto) {
		Produit p = produitMapper.FromDto(produitDto);
		Produit newProduit = produitService.ajouterProduit(p);
		return ResponseEntity.ok(newProduit);
	}
    
    @DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProduit(@PathVariable Long id) {
    	produitService.supprimerProduit(id);
		return 	ResponseEntity.ok("Suppression avec succès");
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateProduit(@PathVariable Long id, @Valid @RequestBody ProduitDto produitDto) {
		Produit p = produitMapper.FromDto(produitDto);
		 produitService.mettreAJourProduit(id, p);
		return ResponseEntity.ok("Mise à jour avec succès");
	}
	@GetMapping("/search")
	public ResponseEntity<List<ProduitDto>> searchProduits(@RequestParam String nom) {
		List<Produit> produits = produitService.findByNom(nom);
		List<ProduitDto> produitsDto = produitMapper.toDtos(produits);
		return ResponseEntity.ok(produitsDto);
	}
    
   
}