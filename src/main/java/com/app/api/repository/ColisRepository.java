package com.app.api.repository;

import com.app.api.entity.Colis;
import com.app.api.entity.Livreur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ColisRepository extends JpaRepository<Colis,String>, JpaSpecificationExecutor<Colis> {

    List<Colis> getAllByLivreur(Livreur livreur);
}
