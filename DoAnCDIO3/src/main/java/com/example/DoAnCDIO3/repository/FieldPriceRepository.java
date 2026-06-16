package com.example.DoAnCDIO3.repository;

import com.example.DoAnCDIO3.entity.FieldPrice;
import com.example.DoAnCDIO3.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FieldPriceRepository extends JpaRepository<FieldPrice,Integer> {
    @Query("SELECT fp FROM FieldPrice fp WHERE fp.field_id.id = :fieldId")
    Page<FieldPrice> getPricesByFieldId(Integer fieldId, Pageable pageable);

    @Query("SELECT fp FROM FieldPrice fp WHERE fp.field_id.id = :fieldId " +
            "AND fp.day_type = :dayType " +
            "AND fp.status = 1 " +
            "AND fp.start_time <= :time AND fp.end_time > :time")
    Optional<FieldPrice> findPriceForBooking(
            @Param("fieldId") Integer fieldId,
            @Param("dayType") Integer dayType,
            @Param("time") LocalTime time
    );

    // Lấy toàn bộ danh sách giá của 1 sân (không phân trang)
    @Query("SELECT fp FROM FieldPrice fp WHERE fp.field_id.id = :fieldId")
    List<FieldPrice> findAllPricesByFieldId(@Param("fieldId") Integer fieldId);

    // Lấy danh sách khung giờ & giá tiền theo Loại ngày (1: Ngày thường, 2: Cuối tuần)
    @Query("SELECT fp FROM FieldPrice fp WHERE fp.field_id.id = :fieldId AND fp.day_type = :dayType AND fp.status = 1")
    List<FieldPrice> findActivePricesByDayType(
            @Param("fieldId") Integer fieldId,
            @Param("dayType") Integer dayType
    );

}
