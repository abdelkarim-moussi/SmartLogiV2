package com.app.api.repository;

import com.app.api.entity.Colis;
import com.app.api.entity.Livreur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ColisRepository extends JpaRepository<Colis,String>, JpaSpecificationExecutor<Colis> {

    List<Colis> getAllByLivreur(Livreur livreur);

    @Query("SELECT c FROM Colis c " +
            "LEFT JOIN FETCH c.livreur " +
            "LEFT JOIN FETCH c.clientExpediteur " +
            "LEFT JOIN FETCH c.destinataire " +
            "LEFT JOIN FETCH c.zone " +
            "WHERE c.id = :id")
    Optional<Colis> findByIdWithRelations(@Param("id") String id);

    // Fetch collections separately
    @Query("SELECT c FROM Colis c " +
            "LEFT JOIN FETCH c.historiqueLivraison " +
            "WHERE c.id = :id")
    Optional<Colis> findByIdWithHistorique(@Param("id") String id);

    @Query("SELECT c FROM Colis c " +
            "LEFT JOIN FETCH c.colisProduits cp " +
            "LEFT JOIN FETCH cp.produit " +
            "WHERE c.id = :id")
    Optional<Colis> findByIdWithProduits(@Param("id") String id);
}
