package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.dto.BookDetailsDto;
import com.example.demo.dto.ReviewDto;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.model.Book;
import com.example.demo.service.ReviewService;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockitoBean private ReviewService reviewService;

    @InjectMocks private ReviewController reviewController;

    @Test
    void createBookWithReview() {}

    @Test
    void findBookUsingIsbnCode() throws BookNotFoundException, Exception {
        // Given
        String isbnCode = "123";
        BookDetailsDto bookDetailsDto =
                BookDetailsDto.builder()
                        .book(
                                Book.builder()
                                        .id(UUID.randomUUID())
                                        .isbnCode(isbnCode)
                                        .title("Title")
                                        .author("Author")
                                        .publishedYear(Year.now())
                                        .build())
                        .reviews(
                                List.of(
                                        ReviewDto.builder()
                                                .id(UUID.randomUUID())
                                                .bookId(UUID.randomUUID()) // to be same as the
                                                // original book
                                                .reviewerName("Name")
                                                .rating(2)
                                                .comment("Comment")
                                                .reviewedOn(LocalDate.now())
                                                .build()))
                        .avgRating(1.0)
                        .totalReviews(1)
                        .ratingDistribution(Map.of(1, 1L))
                        .build();

        // When
        Mockito.when(reviewService.getBook(isbnCode)).thenReturn(bookDetailsDto);

        // Then
        mockMvc.perform(get("/api/books/{isbnCode}", isbnCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.avgRating").value(1.0))
                .andExpect(jsonPath("$.totalReviews").value(1));
    }
}
