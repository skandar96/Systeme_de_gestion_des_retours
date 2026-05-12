package com.example.project;

import com.example.project.Model.Produit;
import com.example.project.Model.RetourProduit;
import com.example.project.Model.Utilisateur;
import com.example.project.Repository.ProduitRepository;
import com.example.project.Repository.RetourProduitRepository;
import com.example.project.Repository.UtilisateurRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest

public class RetourProduitControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RetourProduitRepository retourProduitRepository;

    private Produit produit;
    private Utilisateur utilisateur;

    @BeforeEach
    public void setup() {
        retourProduitRepository.deleteAll();
        produitRepository.deleteAll();
        utilisateurRepository.deleteAll();

        produit = new Produit();
        produit.setNom("Test Produit");
        produit.setDescription("Desc");
        produit.setPrix(10.0);
        produit.setQuantite(5);
        produit = produitRepository.save(produit);

        utilisateur = new Utilisateur();
        utilisateur.setNom("Client Test");
        utilisateur.setEmail("client@test.com");
        utilisateur.setPassword("pass");
        utilisateur.setRole(null);
        utilisateur = utilisateurRepository.save(utilisateur);
    }

    @AfterEach
    public void tearDown() {
        retourProduitRepository.deleteAll();
        produitRepository.deleteAll();
        utilisateurRepository.deleteAll();
    }

    @Test
    public void testCreateReadUpdateDeleteAndSearch() throws Exception {
        // Create
        RetourProduit r = new RetourProduit();
        r.setProduit(produit);
        r.setClient(utilisateur);
        r.setRaison("Defect");
        r.setEtatTraitement("NEW");
        r.setDate(LocalDateTime.now());

        String json = objectMapper.writeValueAsString(r);

        String content = mockMvc.perform(post("/api/retours/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        RetourProduit created = objectMapper.readValue(content, RetourProduit.class);
        assertThat(created.getId()).isNotNull();

        Long id = created.getId();

        // Get
        mockMvc.perform(get("/api/retours/get/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        // Get All
        mockMvc.perform(get("/api/retours/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id));

        // Update
        created.setRaison("Changed");
        String updateJson = objectMapper.writeValueAsString(created);
        mockMvc.perform(put("/api/retours/update/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/retours/get/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.raison").value("Changed"));

        // Search by etat
        mockMvc.perform(get("/api/retours/search").param("etat", "NEW"))
                .andExpect(status().isOk());

        // By produit
        mockMvc.perform(get("/api/retours/byProduit").param("produitId", produit.getId().toString()))
                .andExpect(status().isOk());

        // Delete
        mockMvc.perform(delete("/api/retours/delete/" + id))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/retours/get/" + id))
                .andExpect(status().isNotFound());
    }
}
