package com.app.api.repository;

import com.app.api.entity.Destinataire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DestinataireRepository extends JpaRepository<Destinataire,String> {

    Optional<Destinataire> findByEmail(String email);
}
