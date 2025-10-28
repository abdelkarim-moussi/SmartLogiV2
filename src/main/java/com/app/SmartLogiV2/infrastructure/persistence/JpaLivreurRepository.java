package com.app.SmartLogiV2.infrastructure.persistence;

import com.app.SmartLogiV2.domain.model.Livreur;
import com.app.SmartLogiV2.domain.repository.LivreurRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLivreurRepository extends JpaRepository<Livreur,String>, LivreurRepository {
}
