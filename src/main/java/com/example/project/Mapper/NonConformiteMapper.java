package com.example.project.Mapper;

import com.example.project.Dto.NonConformiteDto;
import com.example.project.Model.NonConformite;
import com.example.project.Model.RetourProduit;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NonConformiteMapper {
    public NonConformiteDto toDto(NonConformite nc) {
        if (nc == null) return null;
        NonConformiteDto dto = new NonConformiteDto();
        dto.setId(nc.getId());
        dto.setDescription(nc.getDescription());
        dto.setGravite(nc.getGravite());
        dto.setDate(nc.getDate());
        dto.setRetourProduitId(nc.getRetourProduit() != null ? nc.getRetourProduit().getId() : null);
        return dto;
    }

    public NonConformite fromDto(NonConformiteDto dto) {
        if (dto == null) return null;
        NonConformite nc = new NonConformite();
        nc.setId(dto.getId());
        nc.setDescription(dto.getDescription());
        nc.setGravite(dto.getGravite());
        nc.setDate(dto.getDate());
        if (dto.getRetourProduitId() != null) {
            RetourProduit rp = new RetourProduit();
            rp.setId(dto.getRetourProduitId());
            nc.setRetourProduit(rp);
        }
        return nc;
    }

    public List<NonConformiteDto> toDtos(List<NonConformite> ncs) {
        return ncs.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<NonConformite> fromDtos(List<NonConformiteDto> dtos) {
        return dtos.stream().map(this::fromDto).collect(Collectors.toList());
    }
}
