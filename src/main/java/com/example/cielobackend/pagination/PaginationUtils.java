package com.example.cielobackend.pagination;

import com.example.cielobackend.exception.InvalidOrderByException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static com.example.cielobackend.common.ExceptionMessages.*;

public class PaginationUtils {
    public static Sort getSortOrder(String sortBy, String orderBy) {
        if ("asc".equalsIgnoreCase(orderBy)) {
            return Sort.by(sortBy).ascending();
        } else if ("desc".equalsIgnoreCase(orderBy)) {
            return Sort.by(sortBy).descending();
        } else {
            throw new InvalidOrderByException(INVALID_ORDER_BY);
        }
    }

    public static Pageable createPageable(Integer page, Integer limit, String sortBy, String orderBy) {
        Sort sortingOrder = getSortOrder(sortBy, orderBy);
        return PageRequest.of(page - 1, limit, sortingOrder);
    }
}
