package com.example.project.Service;

import com.example.project.Model.NonConformite;
import com.example.project.Model.RetourProduit;
import com.example.project.Repository.NonConformiteRepository;
import com.example.project.Repository.RetourProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class NonConformiteService {
    @Autowired
    private NonConformiteRepository nonConformiteRepository;

    @Autowired
    private RetourProduitRepository retourProduitRepository;

    public List<NonConformite> getAllNonConformites() {
        return nonConformiteRepository.findAll();
    }

    public NonConformite getNonConformiteById(Long id) {
        return nonConformiteRepository.findById(id).orElse(null);
    }

    public ResponseEntity<String> ajouterNonConformite(NonConformite nc) {
        if (nc.getRetourProduit() == null || nc.getRetourProduit().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "RetourProduit id is required");
        }
        Long retourId = nc.getRetourProduit().getId();
        RetourProduit retour = retourProduitRepository.findById(retourId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RetourProduit not found"));

        nc.setRetourProduit(retour);
        nonConformiteRepository.save(nc);
        return ResponseEntity.ok("NonConformite ajouté avec succès");
    }

    public ResponseEntity<String> supprimerNonConformite(Long id) {
        nonConformiteRepository.findById(id).ifPresentOrElse(
                nc -> nonConformiteRepository.delete(nc),
                () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NonConformite not found"); }
        );
        return ResponseEntity.ok("Suppression avec succès");
    }

    public ResponseEntity<String> mettreAJourNonConformite(Long id, NonConformite nc) {
        nonConformiteRepository.findById(id).ifPresentOrElse(
                existing -> {
                    existing.setDescription(nc.getDescription());
                    existing.setGravite(nc.getGravite());
                    existing.setDate(nc.getDate());

                    if (nc.getRetourProduit() != null) {
                        if (nc.getRetourProduit().getId() == null) {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "RetourProduit id is required");
                        }
                        RetourProduit retour = retourProduitRepository.findById(nc.getRetourProduit().getId())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RetourProduit not found"));
                        existing.setRetourProduit(retour);
                    }

                    nonConformiteRepository.save(existing);
                },
                () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NonConformite not found"); }
        );
        return ResponseEntity.ok("Mise à jour avec succès");
    }

    public List<NonConformite> findByRetourProduitId(Long retourId) {
        return nonConformiteRepository.findByRetourProduit_Id(retourId);
    }
}
