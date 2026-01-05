package com.example.demo.dto;

import java.time.Year;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookRequestDto {
    private String title;
    private String author;
    private Year publishedYear;
}
