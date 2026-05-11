package com.example.DoAnCDIO3.repository;

import com.example.DoAnCDIO3.entity.FieldSchedule;
import com.example.DoAnCDIO3.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldScheduleRepository extends JpaRepository<FieldSchedule,Integer> {
}
