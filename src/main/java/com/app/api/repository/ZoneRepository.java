package com.app.api.repository;

import com.app.api.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneRepository extends JpaRepository<Zone,String> {
}
