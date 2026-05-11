package com.example.DoAnCDIO3.repository;

import com.example.DoAnCDIO3.entity.Field;
import com.example.DoAnCDIO3.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<Field,Integer> {
    // Tìm kiếm sân theo trạng thái (có phân trang)
    Page<Field> findByStatus(Integer status, Pageable pageable);
}
