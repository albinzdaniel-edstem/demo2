package com.example.demo.service;

import static com.example.demo.mapper.ReviewDtoMapper.toDto;

import com.example.demo.dto.BookDetailsDto;
import com.example.demo.dto.CreateBookRequestDto;
import com.example.demo.dto.CreateBookResponseDto;
import com.example.demo.dto.ReviewDto;
import com.example.demo.exception.BookAlreadyExistsException;
import com.example.demo.exception.BookNotFoundException;
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

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    public CreateBookResponseDto createBook(CreateBookRequestDto request)
            throws BookAlreadyExistsException {
        if (bookRepository.existsBookByIsbnCode(request.getIsbnCode())) {
            throw new BookAlreadyExistsException(request.getIsbnCode());
        }

        Book newBook =
                Book.builder()
                        .isbnCode(request.getIsbnCode())
                        .title(request.getTitle())
                        .author(request.getAuthor())
                        .publishedYear(request.getPublishedYear())
                        .build();

        Book savedBook = bookRepository.save(newBook);

        Review newReview =
                Review.builder()
                        .book(savedBook)
                        .reviewerName(request.getReviewerName())
                        .rating(request.getRating())
                        .comment(request.getComment())
                        .reviewedOn(request.getReviewedOn())
                        .build();
        Review savedReview = reviewRepository.save(newReview);

        return CreateBookResponseDto.builder()
                .book(savedBook)
                .reviewDto(toDto(savedReview))
                .build();
    }

    public BookDetailsDto getBook(String isbnCode) throws BookNotFoundException {
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
                    .book(book)
                    .reviews(reviewDtoList)
                    .avgRating(avgRating)
                    .totalReviews(totalReviews)
                    .ratingDistribution(ratingDistribution)
                    .build();
        } else throw new BookNotFoundException(isbnCode);
    }
}
