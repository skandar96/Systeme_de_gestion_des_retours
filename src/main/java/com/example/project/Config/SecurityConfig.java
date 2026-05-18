package com.example.project.Config;

import com.example.project.Security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

 private final JwtAuthFilter jwtAuthFilter;
 private final UserDetailsService userDetailsService;

 @Bean
 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     http
     	.cors(cors -> {})
         .csrf(AbstractHttpConfigurer::disable)
         .authorizeHttpRequests(auth -> auth
             .requestMatchers(
                 "/api/auth/**",
                 "/swagger-ui/**",
                 "/swagger-ui.html",
                 "/v3/api-docs/**"
             ).permitAll()
             .requestMatchers(
            	        HttpMethod.OPTIONS,
            	        "/**"
            	    ).permitAll()
             .anyRequest().authenticated()
         )
         .sessionManagement(s ->
             s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         )
         .authenticationProvider(authenticationProvider())
         .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

     return http.build();
 }

 @Bean
 public AuthenticationProvider authenticationProvider() {
     // Provide a minimal AuthenticationProvider implementation that uses the
     // project's UserDetailsService and PasswordEncoder. This avoids relying
     // on DaoAuthenticationProvider constructors/setters that may not exist in
     // the Spring Security version used by the project.
     return new AuthenticationProvider() {
         @Override
         public Authentication authenticate(Authentication authentication) throws AuthenticationException {
             String username = authentication.getName();
             String password = (authentication.getCredentials() == null) ? null : authentication.getCredentials().toString();
             UserDetails user = userDetailsService.loadUserByUsername(username);
             if (password == null || !passwordEncoder().matches(password, user.getPassword())) {
                 throw new BadCredentialsException("Bad credentials");
             }
             return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
         }

         @Override
         public boolean supports(Class<?> authentication) {
             return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
         }
     };
 }

 @Bean
 public PasswordEncoder passwordEncoder() {
     return new BCryptPasswordEncoder();
 }

 @Bean
 public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
         throws Exception {
     return config.getAuthenticationManager();
 }
}