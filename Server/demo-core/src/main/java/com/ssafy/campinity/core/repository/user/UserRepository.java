package com.ssafy.campinity.core.repository.user;

import com.ssafy.campinity.core.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUuid(UUID userId);
}
