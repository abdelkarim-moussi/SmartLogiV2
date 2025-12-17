package com.app.api.repository;

import com.app.api.entity.UserInfo;
import com.app.api.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo,String> {
    Optional<UserInfo> findByEmail(String email);
}
