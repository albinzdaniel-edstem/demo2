package com.example.demo.mapper;

import com.example.demo.dto.ReviewDto;
import com.example.demo.model.Review;

public class ReviewDtoMapper {
    public static ReviewDto toDto(Review review) {
        return ReviewDto.builder()
				.id(review.getId())
                .bookId(review.getBook().getId())
                .reviewerName(review.getReviewerName())
                .reviewedOn(review.getReviewedOn())
                .rating(review.getRating())
                .comment(review.getComment())
                .build();
    }
}
