package com.smarttraffic.traffic_management_system.repository;

import com.smarttraffic.traffic_management_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

}
