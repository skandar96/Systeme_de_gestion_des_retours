package com.example.project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.Dto.RegisterRequest;
import com.example.project.Model.Utilisateur;
import com.example.project.Service.UserService;

@RestController
@RequestMapping("/api/utilisateurs")

public class UserController {
	
	
	
	@Autowired
	private UserService userService;
	
	
	
	@GetMapping("/all")
	public ResponseEntity<List<Utilisateur>> getAllUsers() {
	    return ResponseEntity.ok(userService.Getall());
	}
	@GetMapping("/get/{id}")
	public ResponseEntity<Utilisateur> getUserById(@PathVariable Long id) {
	    Utilisateur user = userService.findById(id);
	    if (user == null) {
	        return ResponseEntity.status(404).body(null);
	    }
	    return ResponseEntity.ok(user);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		return userService.deleteById(id);
	}
	@PutMapping("/update/{id}")
	public ResponseEntity<Utilisateur> updateUser(@PathVariable Long id, @RequestBody RegisterRequest request) {
	    Utilisateur updatedUser = userService.updateUser(id, request);
	    return ResponseEntity.ok(updatedUser);
	}

}
