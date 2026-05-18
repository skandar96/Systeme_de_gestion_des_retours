package com.example.project.Mapper;

import com.example.project.Dto.NonConformiteDto;
import com.example.project.Model.NonConformite;
import com.example.project.Model.RetourProduit;
import com.example.project.Model.Utilisateur;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NonConformiteMapper {
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	
    public NonConformiteDto toDto(NonConformite nc) {
        if (nc == null) return null;
        NonConformiteDto dto = modelMapper.map(nc, NonConformiteDto.class);
        dto.setRetourProduitId(nc.getRetourProduit().getId());
        dto.setAdminId(nc.getAdmin().getId());
        dto.setAdminName(nc.getAdmin().getNom());
        return dto;
    }

    public NonConformite fromDto(NonConformiteDto dto) {
    	NonConformite nc = modelMapper.map(dto, NonConformite.class);
    			RetourProduit rp = new RetourProduit();
    			rp.setId(dto.getRetourProduitId());
    			nc.setRetourProduit(rp);
    			Utilisateur admin = new Utilisateur();
    			admin.setId(dto.getAdminId());
    			admin.setNom(dto.getAdminName());
    			nc.setAdmin(admin);
    			
    			
        return nc;
    }

    public List<NonConformiteDto> toDtos(List<NonConformite> ncs) {
        return ncs.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<NonConformite> fromDtos(List<NonConformiteDto> dtos) {
        return dtos.stream().map(this::fromDto).collect(Collectors.toList());
    }
}
