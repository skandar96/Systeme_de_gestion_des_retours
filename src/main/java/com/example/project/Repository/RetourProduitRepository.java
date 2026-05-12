package com.example.project.Repository;

import com.example.project.Model.RetourProduit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetourProduitRepository extends JpaRepository<RetourProduit, Long> {
    List<RetourProduit> findByEtatTraitement(String etatTraitement);
    List<RetourProduit> findByProduit_Id(Long produitId);
}
