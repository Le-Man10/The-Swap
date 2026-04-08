package com.example.TailorMe.API.Repositories;

import com.example.TailorMe.API.Models.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<user,Long> {
    boolean existsByEmail(String email);
    user findByEmail(String email);
}
