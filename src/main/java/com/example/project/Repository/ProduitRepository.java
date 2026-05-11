package com.example.project.Repository;

import com.example.project.Model.Produit;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
	
	List<Produit> findByNom(String nom);
	
	

}