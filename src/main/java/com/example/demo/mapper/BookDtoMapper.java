package com.example.demo.mapper;

import com.example.demo.dto.BookDto;
import com.example.demo.model.Book;

public class BookDtoMapper {
    public static BookDto toDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .isbnCode(book.getIsbnCode())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publishedYear(book.getPublishedYear())
                .reviewCount(book.getReviews().size())
                .build();
    }
}
