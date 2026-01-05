package com.example.demo.dto;

import java.time.Year;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private UUID id;
    private String isbnCode;
    private String title;
    private String author;
    private Year publishedYear;
    private int reviewCount;
}
