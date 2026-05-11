package com.example.DoAnCDIO3.service;


import com.example.DoAnCDIO3.dto.PageResponse;
import com.example.DoAnCDIO3.dto.request.BookingCreateRequest;
import com.example.DoAnCDIO3.dto.response.BookingResponse;

public interface BookingService {
    public BookingResponse createBooking(Integer customerId, BookingCreateRequest request);

    public PageResponse<BookingResponse> getAllBookings(int page, int size);
}
