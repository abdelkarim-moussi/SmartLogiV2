package com.app.api.repository;

import com.app.api.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit,String> {
}
