package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import java.time.Year;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequestDto {
    private String isbnCode;
    private String title;
    private String author;
    private Year publishedYear;
    private String reviewerName;

    @Min(1)
    @Max(5)
    private int rating;

    private String comment;
    private LocalDate reviewedOn;
}
