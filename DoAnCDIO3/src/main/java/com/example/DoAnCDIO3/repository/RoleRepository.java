package com.example.DoAnCDIO3.repository;

import com.example.DoAnCDIO3.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(String name);
    public boolean existsByName(String name);
}
