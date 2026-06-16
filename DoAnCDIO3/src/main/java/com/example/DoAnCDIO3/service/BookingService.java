package com.example.DoAnCDIO3.service;


import com.example.DoAnCDIO3.dto.PageResponse;
import com.example.DoAnCDIO3.dto.request.BookingCreateRequest;
import com.example.DoAnCDIO3.dto.response.BookingResponse;

public interface BookingService {
    public BookingResponse createBooking(Integer customerId, BookingCreateRequest request);

    PageResponse<BookingResponse> getBookingsByOwner(Integer ownerId, int page, int size);

    BookingResponse processBooking(Integer bookingId, Integer ownerId, Integer status);

    PageResponse<BookingResponse> getBookingsByCustomer(Integer customerId, int page, int size);
}
