package com.example.DoAnCDIO3.repository;

import com.example.DoAnCDIO3.entity.Booking;
import com.example.DoAnCDIO3.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Booking b " +
            "WHERE b.field_id.id = :fieldId " + // Lưu ý: thay .id bằng tên trường id trong class Field của bạn
            "AND b.booking_date = :bookingDate " +
            "AND b.status != 2 " + // Giả sử status 2 là Đã Hủy, thì không tính trùng lịch
            "AND (b.start_time < :endTime AND b.end_time > :startTime)")
    boolean existsOverlappingBooking(
            @Param("fieldId") Integer fieldId,
            @Param("bookingDate") LocalDate bookingDate,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );
}
