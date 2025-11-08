package com.app.api.repository;

import com.app.api.entity.Colis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ColisRepository extends JpaRepository<Colis,String>, JpaSpecificationExecutor<Colis> {}
