package com.example.project.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.project.Model.RetourProduit;
import com.example.project.Model.Produit;
import com.example.project.Model.Utilisateur;
import com.example.project.Repository.RetourProduitRepository;
import com.example.project.Repository.ProduitRepository;
import com.example.project.Repository.UtilisateurRepository;

@Service
public class RetourProduitService {
    @Autowired
    private RetourProduitRepository retourProduitRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<RetourProduit> getAllRetours() {
        return retourProduitRepository.findAll();
    }

    public RetourProduit getRetourById(Long id) {
        return retourProduitRepository.findById(id).orElse(null);
    }

    public RetourProduit ajouterRetour(RetourProduit r) {
        // validate produit
        if (r.getProduit() == null || r.getProduit().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produit id is required");
        }
        Long produitId = r.getProduit().getId();
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit not found"));

        // validate client
        if (r.getClient() == null || r.getClient().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client id is required");
        }
        Long clientId = r.getClient().getId();
        Utilisateur client = utilisateurRepository.findById(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));

        // set managed entities
        r.setProduit(produit);
        r.setClient(client);

        return retourProduitRepository.save(r);
    }

    public ResponseEntity<String> supprimerRetour(Long id) {
        retourProduitRepository.findById(id).ifPresentOrElse(
                r -> {
                    retourProduitRepository.delete(r);
                },
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Retour not found");
                }
        );
        return ResponseEntity.ok("Suppression avec succès");
    }

    public ResponseEntity<String> mettreAJourRetour(Long id, RetourProduit r) {
        retourProduitRepository.findById(id).ifPresentOrElse(
                existing -> {
                    existing.setRaison(r.getRaison());
                    existing.setEtatTraitement(r.getEtatTraitement());
                    existing.setDate(r.getDate());
                    existing.setQuantite(r.getQuantite());
                    

                    // if produit provided, validate and set
                    if (r.getProduit() != null) {
                        if (r.getProduit().getId() == null) {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produit id is required");
                        }
                        Produit produit = produitRepository.findById(r.getProduit().getId())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit not found"));
                        existing.setProduit(produit);
                    }
                    

                    retourProduitRepository.save(existing);
                },
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Retour not found");
                }
        );
        return ResponseEntity.ok("Mise à jour avec succès");
    }

    public List<RetourProduit> findByEtatTraitement(String etat) {
        return retourProduitRepository.findByEtatTraitement(etat);
    }

    public List<RetourProduit> findByProduitId(Long produitId) {
        return retourProduitRepository.findByProduit_Id(produitId);
    }
}