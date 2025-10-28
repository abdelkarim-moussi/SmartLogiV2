package com.app.SmartLogiV2.domain.repository;

import com.app.SmartLogiV2.domain.model.Colis;

import java.util.List;
import java.util.Optional;

public interface ColisRepository {

    Optional<Colis> findById(Long id);
    List<Colis> findAll();
    Colis save(Colis colis);
    void delete(Long id);
    Colis update(Long id, Colis colis);

}
