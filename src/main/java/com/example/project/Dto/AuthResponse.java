package com.example.project.Dto;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
 private String token;
 private String email;
 private String role;
}