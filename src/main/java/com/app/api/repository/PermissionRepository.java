package com.app.api.repository;

import com.app.api.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
    Optional<Permission> findByName(String name);
    boolean existsByName(String name);
    void deleteByName(String name);

    @Query("SELECT COUNT(p) FROM Permission p")
    int countPermissions();
}
