package com.example.project.Controller;



import com.example.project.Dto.AuthResponse;
import com.example.project.Dto.LoginRequest;
import com.example.project.Dto.RegisterRequest;
import com.example.project.Model.Utilisateur;
import com.example.project.Security.JwtService;
import com.example.project.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentification")
public class AuthController {

 private final UserService userService;
 private final JwtService jwtService;
 private final AuthenticationManager authenticationManager;

 @PostMapping("/register")
 @Operation(summary = "Créer un compte utilisateur")
 public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
     Utilisateur user = userService.register(request);
     String token = jwtService.generateToken(user);
     return ResponseEntity.ok(new AuthResponse(token, user.getEmail(), user.getRole().name()));
 }

 @PostMapping("/login")
 @Operation(summary = "Se connecter et obtenir un token JWT")
 public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
     authenticationManager.authenticate(
             new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
     );
     Utilisateur user = userService.findByEmail(request.getEmail());
     String token = jwtService.generateToken(user);
     return ResponseEntity.ok(new AuthResponse(token, user.getEmail(), user.getRole().name()));
 }
}