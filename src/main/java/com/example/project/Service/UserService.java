package com.example.project.Service;

import com.example.project.Dto.RegisterRequest;
import com.example.project.Model.Role;
import com.example.project.Model.Utilisateur;
import com.example.project.Repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

 private final UtilisateurRepository utilisateurRepository;
 private final PasswordEncoder passwordEncoder;

 public Utilisateur register(RegisterRequest request) {
     if (utilisateurRepository.existsByEmail(request.getEmail())) {
         throw new RuntimeException("Email déjà utilisé : " + request.getEmail());
     }

     Utilisateur user = Utilisateur.builder()
             .nom(request.getNom())
             .email(request.getEmail())
             .password(passwordEncoder.encode(request.getPassword()))
             .role(request.getRole() != null ? request.getRole() : Role.EMPLOYE)
             .build();

     return utilisateurRepository.save(user);
 }

 public Utilisateur findByEmail(String email) {
     return utilisateurRepository.findByEmail(email)
             .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
 }
 
 public Utilisateur findById(Long id) {
	 return utilisateurRepository.findById(id)
			 .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
 }
 public List<Utilisateur> Getall() {
	 return utilisateurRepository.findAll();
 }
 public ResponseEntity<String> deleteById(Long id) {
	 if (!utilisateurRepository.existsById(id)) {
		 return ResponseEntity.notFound().build();
	 }
	 utilisateurRepository.deleteById(id);
	 return ResponseEntity.ok("Utilisateur supprimé avec succès");
 }
 public Utilisateur updateUser(Long id, RegisterRequest request) {
	 Utilisateur u = utilisateurRepository.findById(id).
			 orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
	 u.setNom(request.getNom());
	 u.setEmail(request.getEmail());
	 if (request.getRole() != null) {
		 u.setRole(request.getRole());
	 }
	 return utilisateurRepository.save(u);
 }
 
}