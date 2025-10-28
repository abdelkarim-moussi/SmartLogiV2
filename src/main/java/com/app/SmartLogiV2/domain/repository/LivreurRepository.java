package com.app.SmartLogiV2.domain.repository;

import com.app.SmartLogiV2.domain.model.Livreur;

import java.util.List;
import java.util.Optional;

public interface LivreurRepository {

    Optional<Livreur> findById(String id);
    List<Livreur> findAll();
    Livreur save(Livreur livreur);
    void delete(String id);
    Livreur update(String id,Livreur livreur);

}
