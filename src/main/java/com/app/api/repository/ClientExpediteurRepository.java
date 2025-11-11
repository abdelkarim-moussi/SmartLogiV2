package com.app.api.repository;

import com.app.api.entity.ClientExpediteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientExpediteurRepository extends JpaRepository<ClientExpediteur,String> {
    public Optional<ClientExpediteur> findByEmail(String email);
}
