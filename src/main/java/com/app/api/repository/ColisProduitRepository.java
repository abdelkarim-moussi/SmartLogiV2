package com.app.api.repository;

import com.app.api.entity.ColisProduit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColisProduitRepository extends JpaRepository<ColisProduit,String> {
}
