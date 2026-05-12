package com.example.project.Mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.project.Dto.RetourProduitDto;
import com.example.project.Model.NonConformite;
import com.example.project.Model.Produit;
import com.example.project.Model.RetourProduit;
import com.example.project.Model.Utilisateur;

@Component
public class RetourProduitMapper {
    @Autowired
    private ModelMapper mmMapper;

    public RetourProduitDto toDto(RetourProduit r) {
        if (r == null) return null;
        RetourProduitDto dto = new RetourProduitDto();
        dto.setId(r.getId());
        dto.setProduitId(r.getProduit() != null ? r.getProduit().getId() : null);
        dto.setClientId(r.getClient() != null ? r.getClient().getId() : null);
        dto.setRaison(r.getRaison());
        dto.setEtatTraitement(r.getEtatTraitement());
        dto.setDate(r.getDate());
        if (r.getNonConformite() != null) {
            List<Long> ncIds = r.getNonConformite().stream()
                    .map(NonConformite::getId)
                    .collect(Collectors.toList());
            dto.setNonConformiteIds(ncIds);
        }
        return dto;
    }

    public RetourProduit fromDto(RetourProduitDto dto) {
        if (dto == null) return null;
        RetourProduit r = new RetourProduit();
        r.setId(dto.getId());
        if (dto.getProduitId() != null) {
            Produit p = new Produit();
            p.setId(dto.getProduitId());
            r.setProduit(p);
        }
        if (dto.getClientId() != null) {
            Utilisateur u = new Utilisateur();
            u.setId(dto.getClientId());
            r.setClient(u);
        }
        r.setRaison(dto.getRaison());
        r.setEtatTraitement(dto.getEtatTraitement());
        r.setDate(dto.getDate());
        // nonConformite list mapping left null; typically handled elsewhere
        return r;
    }

    public List<RetourProduitDto> toDtos(List<RetourProduit> rs) {
        return rs.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<RetourProduit> fromDtos(List<RetourProduitDto> dtos) {
        return dtos.stream().map(this::fromDto).collect(Collectors.toList());
    }
}
