package com.app.api.repository;

import com.app.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.roles")
    List<User> findAllWithRoles();

    @Query("SELECT COUNT(u) FROM User u")
    int countUsers();
}
