package com.example.project.Controller;

import com.example.project.Dto.NonConformiteDto;
import com.example.project.Mapper.NonConformiteMapper;
import com.example.project.Model.NonConformite;
import com.example.project.Service.NonConformiteService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nonconformites")
public class NonConformiteController {

    @Autowired
    private NonConformiteService nonConformiteService;

    @Autowired
    private NonConformiteMapper nonConformiteMapper;

    @GetMapping("/all")
    public ResponseEntity<List<NonConformiteDto>> getAll() {
        List<NonConformite> ncs = nonConformiteService.getAllNonConformites();
        return ResponseEntity.ok(nonConformiteMapper.toDtos(ncs));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<NonConformiteDto> getById(@PathVariable Long id) {
        NonConformite nc = nonConformiteService.getNonConformiteById(id);
        if (nc == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(nonConformiteMapper.toDto(nc));
    }

    @PostMapping("/add")
    public ResponseEntity<NonConformiteDto> add(@Valid @RequestBody NonConformiteDto dto) {
        NonConformite nc = nonConformiteMapper.fromDto(dto);
        NonConformite saved = nonConformiteService.ajouterNonConformite(nc);
        return ResponseEntity.ok(nonConformiteMapper.toDto(saved));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return nonConformiteService.supprimerNonConformite(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @Valid @RequestBody NonConformiteDto dto) {
        NonConformite nc = nonConformiteMapper.fromDto(dto);
        return nonConformiteService.mettreAJourNonConformite(id, nc);
    }

    @GetMapping("/byRetour")
    public ResponseEntity<List<NonConformiteDto>> getByRetour(@RequestParam Long retourId) {
        List<NonConformite> ncs = nonConformiteService.findByRetourProduitId(retourId);
        return ResponseEntity.ok(nonConformiteMapper.toDtos(ncs));
    }
}
