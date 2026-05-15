package com.example.DoAnCDIO3.service;

import com.example.DoAnCDIO3.dto.response.FieldScheduleResponse;
import com.example.DoAnCDIO3.entity.Booking;

import java.time.LocalDate;
import java.util.List;

public interface FieldScheduleService {

    void generateSchedulesForBooking(Booking booking);

    // Lấy danh sách giờ đã đặt để Frontend hiển thị màu
    List<FieldScheduleResponse> getBookedSchedules(Integer fieldId, LocalDate date);
}
