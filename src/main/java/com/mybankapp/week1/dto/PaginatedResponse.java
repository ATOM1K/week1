package com.mybankapp.week1.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class PaginatedResponse<T> {
    private List<T> content;
    private long totalElements;
    private int totalPages;
    private int number;       // page
    private int size;
    private boolean first;
    private boolean last;
}