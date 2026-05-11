package com.example.DoAnCDIO3.repository;

import com.example.DoAnCDIO3.entity.FieldType;
import com.example.DoAnCDIO3.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldTypeRepository extends JpaRepository<FieldType,Integer> {
    boolean existsByName(String name);
}
