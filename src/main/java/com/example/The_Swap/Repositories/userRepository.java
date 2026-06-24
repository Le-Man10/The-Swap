package com.example.The_Swap.Repositories;

import com.example.The_Swap.Model.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<user,Long> {
    boolean existsByEmail(String email);
    user findByEmail(String email);
}
