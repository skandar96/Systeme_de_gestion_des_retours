package com.example.project.Controller;

import com.example.project.Dto.RetourProduitDto;
import com.example.project.Mapper.RetourProduitMapper;
import com.example.project.Model.RetourProduit;
import com.example.project.Service.RetourProduitService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/retours")
public class RetourProduitController {

    @Autowired
    private RetourProduitService retourProduitService;
    @Autowired
    private RetourProduitMapper retourProduitMapper;

    @GetMapping("/all")
    public ResponseEntity<List<RetourProduitDto>> getAllRetours() {
        List<RetourProduit> retours = retourProduitService.getAllRetours();
        List<RetourProduitDto> dtos = retourProduitMapper.toDtos(retours);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<RetourProduitDto> getRetourById(@PathVariable Long id) {
        RetourProduit r = retourProduitService.getRetourById(id);
        if (r == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(retourProduitMapper.toDto(r));
    }

    @PostMapping("/add")
    public ResponseEntity<RetourProduitDto> addRetour(@Valid @RequestBody RetourProduitDto dto) {
        RetourProduit r = retourProduitMapper.fromDto(dto);
        RetourProduit saved = retourProduitService.ajouterRetour(r);
        RetourProduitDto savedDto = retourProduitMapper.toDto(saved);
        return ResponseEntity.ok(savedDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRetour(@PathVariable Long id) {
        retourProduitService.supprimerRetour(id);
        return ResponseEntity.ok("Suppression avec succès");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRetour(@PathVariable Long id, @Valid @RequestBody RetourProduitDto dto) {
        RetourProduit r = retourProduitMapper.fromDto(dto);
        retourProduitService.mettreAJourRetour(id, r);
        return ResponseEntity.ok("Mise à jour avec succès");
    }

    @GetMapping("/search")
    public ResponseEntity<List<RetourProduitDto>> searchByEtat(@RequestParam String etat) {
        List<RetourProduit> retours = retourProduitService.findByEtatTraitement(etat);
        return ResponseEntity.ok(retourProduitMapper.toDtos(retours));
    }

    @GetMapping("/byProduit")
    public ResponseEntity<List<RetourProduitDto>> getByProduit(@RequestParam Long produitId) {
        List<RetourProduit> retours = retourProduitService.findByProduitId(produitId);
        return ResponseEntity.ok(retourProduitMapper.toDtos(retours));
    }
}