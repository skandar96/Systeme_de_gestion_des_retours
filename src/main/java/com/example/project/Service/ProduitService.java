package com.example.project.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.Model.Produit;
import com.example.project.Repository.ProduitRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ProduitService {
	@Autowired
	private ProduitRepository produitRepository;
	
	public List<Produit> getAllProduits() {
        return produitRepository.findAll();
	}
	
	public Produit getProduitById(Long id) {
		return produitRepository.findById(id).orElse(null);
	}
	
	public Produit ajouterProduit(Produit produit) {
        return produitRepository.save(produit);
    }
	
	public ResponseEntity<String> supprimerProduit(Long id) {
        produitRepository.findById(id).ifPresentOrElse(
                p -> {
                    produitRepository.delete(p);
                },
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit not found");
                }
        );
        return ResponseEntity.ok("Suppression avec succès");
    }

    public ResponseEntity<String> mettreAJourProduit(Long id, Produit produit) {
        produitRepository.findById(id).ifPresentOrElse(
                p -> {
                    p.setNom(produit.getNom());
                    p.setDescription(produit.getDescription());
                    p.setPrix(produit.getPrix());
                    p.setQuantite(produit.getQuantite());
                    produitRepository.save(p);
                },
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit not found");
                }
        );
        return ResponseEntity.ok("Mise à jour avec succès");
    }

    public List<Produit> findByNom(String nom) {
        return produitRepository.findByNom(nom);
    }
	
    
}