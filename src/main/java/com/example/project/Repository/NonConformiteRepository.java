package com.example.project.Repository;

import com.example.project.Model.NonConformite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NonConformiteRepository extends JpaRepository<NonConformite, Long> {
    List<NonConformite> findByRetourProduit_Id(Long retourProduitId);
}
