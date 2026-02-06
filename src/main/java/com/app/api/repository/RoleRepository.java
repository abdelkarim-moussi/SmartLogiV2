package com.app.api.repository;

import com.app.api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);
    boolean existsByName(String name);
    void deleteByName(String name);

    @Query("SELECT COUNT(r) FROM Role r")
    int countRoles();
}
