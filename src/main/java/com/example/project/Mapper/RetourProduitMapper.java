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
        RetourProduitDto dto = mmMapper.map(r, RetourProduitDto.class);
        dto.setProduitId(r.getProduit().getId());
        dto.setClientId(r.getClient().getId());
        dto.setClientname(r.getClient().getNom());
        dto.setProduitNom(r.getProduit().getNom());
        dto.setQuantite(r.getQuantite());
        return dto;
    }

    public RetourProduit fromDto(RetourProduitDto dto) {
        RetourProduit r = mmMapper.map(dto, RetourProduit.class);
        
        
        Produit p = new Produit();
        p.setId(dto.getProduitId());
        r.setProduit(p);
        Utilisateur client = new Utilisateur();
        client.setId(dto.getClientId());
        client.setNom(dto.getClientname());
        r.setClient(client);
        
        return r;
    }

    public List<RetourProduitDto> toDtos(List<RetourProduit> rs) {
        return rs.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<RetourProduit> fromDtos(List<RetourProduitDto> dtos) {
        return dtos.stream().map(this::fromDto).collect(Collectors.toList());
    }
}
