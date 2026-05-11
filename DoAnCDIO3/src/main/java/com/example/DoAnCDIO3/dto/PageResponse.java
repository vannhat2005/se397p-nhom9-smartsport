package com.example.DoAnCDIO3.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse <T> {
    int currentPage;    // Trang hiện tại
    int totalPages;     // Tổng số trang
    int pageSize;       // Số lượng phần tử trong mỗi trang
    long totalElements; // Tổng số phần tử trong toàn bộ dữ liệu

    @Builder.Default
    private List<T> data = Collections.emptyList();
    // Danh sách dữ liệu của trang hiện tại
}
