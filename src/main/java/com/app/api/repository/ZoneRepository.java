package com.app.api.repository;

import com.app.api.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZoneRepository extends JpaRepository<Zone,String> {

    Optional<Zone> findByCodePostal(String codePostal);
}
