package com.example.demo.dto;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private UUID id;
    private UUID bookId;
    private String reviewerName;
    private int rating;
    private String comment;
    private LocalDate reviewedOn;
}
