package com.example.DoAnCDIO3.service.ServiceImpl;

import com.example.DoAnCDIO3.dto.response.FieldScheduleResponse;
import com.example.DoAnCDIO3.entity.Booking;
import com.example.DoAnCDIO3.entity.FieldSchedule;
import com.example.DoAnCDIO3.repository.FieldScheduleRepository;
import com.example.DoAnCDIO3.service.FieldScheduleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FieldScheduleServiceImpl implements FieldScheduleService {
    FieldScheduleRepository fieldScheduleRepository;
    @Override
    public void generateSchedulesForBooking(Booking booking) {
        LocalTime currentStartTime = booking.getStart_time();
        LocalTime finalEndTime = booking.getEnd_time();

        List<FieldSchedule> schedulesToSave = new ArrayList<>();

        // Cứ cắt đúng 1 tiếng một cách thoải mái, vì dữ liệu đầu vào đã bị ép chẵn 100%
        while (currentStartTime.isBefore(finalEndTime)) {
            LocalTime nextEndTime = currentStartTime.plusHours(1);

            FieldSchedule schedule = FieldSchedule.builder()
                    .field_id(booking.getField_id())
                    .booking_id(booking)
                    .schedule_date(booking.getBooking_date())
                    .start_time(currentStartTime)
                    .end_time(nextEndTime)
                    .status(1) // 1: Đã được đặt
                    .created_at(LocalDateTime.now())
                    .build();

            schedulesToSave.add(schedule);
            currentStartTime = nextEndTime;
        }

        // Lưu vào DB
        fieldScheduleRepository.saveAll(schedulesToSave);
    }

    @Override
    public List<FieldScheduleResponse> getBookedSchedules(Integer fieldId, LocalDate date) {
        // 1. Lấy danh sách Entity từ DB
        List<FieldSchedule> bookedSlots = fieldScheduleRepository.findBookedSlotsByFieldAndDate(fieldId, date);

        // 2. Map sang DTO (Ở đây tôi code tay cho nhanh, nếu sếp có Mapper thì dùng Mapper nhé)
        return bookedSlots.stream()
                .map(slot -> FieldScheduleResponse.builder()
                        .schedule_date(slot.getSchedule_date())
                        .start_time(slot.getStart_time())
                        .end_time(slot.getEnd_time())
                        .status(slot.getStatus())
                        .build())
                .toList();
    }
}
