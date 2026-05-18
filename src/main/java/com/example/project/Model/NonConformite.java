package com.example.project.Model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor 
@AllArgsConstructor
public class NonConformite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	
	@Enumerated(EnumType.STRING)
	private Gravite gravite;
	 
	private LocalDate date;
	
	@ManyToOne
	@JoinColumn(name = "admin_id", nullable = false)
	private Utilisateur admin;
	
	@ManyToOne
	@JoinColumn(name = "retour_produit_id")
	private RetourProduit retourProduit;

}