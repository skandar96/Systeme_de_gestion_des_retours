package com.example.project.Mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.project.Dto.ProduitDto;
import com.example.project.Model.Produit;

@Component
public class ProduitMapper {
	@Autowired
	private ModelMapper mmMapper;
	

	public ProduitDto toDto(Produit p) {
		ProduitDto pDto = mmMapper.map(p, ProduitDto.class);
		return pDto;
	}
	
	
	public Produit FromDto(ProduitDto pDto) {
		return mmMapper.map(pDto, Produit.class);
		
		
	}
	public List<ProduitDto> toDtos(List<Produit> ps) {
		return  ps.stream()
				.map(
						p -> mmMapper.map(p, ProduitDto.class))
				.collect(Collectors.toList());
	}
	
	public List<Produit> fromDtos(List<ProduitDto> pDtos) {
		return  pDtos.stream()
				.map(
						pDto -> mmMapper.map(pDto, Produit.class))
				.collect(Collectors.toList());
	}
}