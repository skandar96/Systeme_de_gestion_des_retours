package com.example.project.Model;

import java.time.LocalDate;

import jakarta.persistence.*;
@Entity
public class NonConformite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	
	@Enumerated(EnumType.STRING)
	private Gravite gravite;
	
	private LocalDate date;
	
	
	@ManyToOne
	@JoinColumn(name = "retour_produit_id")
	private RetourProduit retourProduit;

}
