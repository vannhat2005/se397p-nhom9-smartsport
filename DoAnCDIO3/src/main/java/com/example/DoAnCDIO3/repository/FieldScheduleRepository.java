package com.example.DoAnCDIO3.repository;

import com.example.DoAnCDIO3.entity.FieldSchedule;
import com.example.DoAnCDIO3.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FieldScheduleRepository extends JpaRepository<FieldSchedule,Integer> {

    // Lấy danh sách các khung giờ ĐÃ ĐƯỢC ĐẶT của một sân trong 1 ngày cụ thể
    // Chú ý: fs.field_id.id vì trong Entity bạn đặt tên biến là field_id
    @Query("SELECT fs FROM FieldSchedule fs WHERE fs.field_id.id = :fieldId AND fs.schedule_date = :date AND fs.status = 1")
    List<FieldSchedule> findBookedSlotsByFieldAndDate(
            @Param("fieldId") Integer fieldId,
            @Param("date") LocalDate date
    );
}
