package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.mapper.BookDtoMapper;
import com.example.demo.mapper.ReviewDtoMapper;
import com.example.demo.model.Book;
import com.example.demo.model.Review;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.ReviewRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    @Transactional
    public CreateBookResponseDto createBook(CreateBookRequestDto request) {
        Optional<Book> book = bookRepository.findByIsbnCode(request.getIsbnCode());
        Book savedBook;
        if (book.isEmpty()) {
            Book newBook =
                    Book.builder()
                            .isbnCode(request.getIsbnCode())
                            .title(request.getTitle())
                            .author(request.getAuthor())
                            .publishedYear(request.getPublishedYear())
                            .build();

            savedBook = bookRepository.save(newBook);
        } else savedBook = book.get();

        Review newReview =
                Review.builder()
                        .book(savedBook)
                        .reviewerName(request.getReviewerName())
                        .rating(request.getRating())
                        .comment(request.getComment())
                        .reviewedOn(request.getReviewedOn())
                        .build();

        savedBook.getReviews().add(newReview);

        Review savedReview = reviewRepository.save(newReview);
        savedBook = bookRepository.save(savedBook);

        return CreateBookResponseDto.builder()
                .bookDto(BookDtoMapper.toDto(savedBook))
                .reviewDto(ReviewDtoMapper.toDto(savedReview))
                .build();
    }

    public BookDetailsDto getBook(String isbnCode) {
        Optional<Book> optionalBook = bookRepository.findByIsbnCode(isbnCode);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            List<Review> reviewList = reviewRepository.findByBook_IsbnCode(isbnCode);
            List<ReviewDto> reviewDtoList =
                    reviewList.stream().map(ReviewDtoMapper::toDto).toList();
            Double avgRating =
                    reviewList.stream().mapToDouble(Review::getRating).average().orElse(0.0);
            int totalReviews = reviewList.size();
            Map<Integer, Long> ratingDistribution =
                    reviewList.stream()
                            .collect(
                                    Collectors.groupingBy(
                                            Review::getRating, Collectors.counting()));
            return BookDetailsDto.builder()
                    .bookDto(BookDtoMapper.toDto(book))
                    .reviews(reviewDtoList)
                    .avgRating(avgRating)
                    .totalReviews(totalReviews)
                    .ratingDistribution(ratingDistribution)
                    .build();
        } else throw new BookNotFoundException(isbnCode);
    }

    public UpdateBookResponseDto updateBook(String isbnCode, UpdateBookRequestDto request) {
        Optional<Book> optionalBook = bookRepository.findByIsbnCode(isbnCode);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (request.getTitle() != null) {
                book.setTitle(request.getTitle());
            }
            if (request.getAuthor() != null) {
                book.setAuthor(request.getAuthor());
            }
            if (request.getPublishedYear() != null) {
                book.setPublishedYear(request.getPublishedYear());
            }
			bookRepository.save(book);
            return UpdateBookResponseDto.builder()
                    .id(book.getId())
                    .isbnCode(book.getIsbnCode())
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .publishedYear(book.getPublishedYear())
                    .build();

        } else throw new BookNotFoundException(isbnCode);
    }

    public void deleteBook(String isbnCode) {
        Optional<Book> optionalBook = bookRepository.findByIsbnCode(isbnCode);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException(isbnCode);
        }
        bookRepository.delete(optionalBook.get());
    }
}
