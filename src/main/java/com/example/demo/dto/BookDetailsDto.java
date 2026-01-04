package com.example.demo.dto;

import com.example.demo.model.Book;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailsDto {
    Book book;
    List<ReviewDto> reviews;
    Double avgRating;
    int totalReviews;
    Map<Integer, Long> ratingDistribution;
}
