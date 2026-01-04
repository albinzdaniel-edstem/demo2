package com.example.demo.controller;

import com.example.demo.dto.BookDetailsDto;
import com.example.demo.dto.CreateBookRequestDto;
import com.example.demo.dto.CreateBookResponseDto;
import com.example.demo.exception.BookAlreadyExistsException;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<CreateBookResponseDto> createBookWithReview(
            @RequestBody CreateBookRequestDto request) throws BookAlreadyExistsException {
        return new ResponseEntity<>(reviewService.createBook(request), HttpStatus.CREATED);
    }

    @GetMapping("/{isbnCode}")
    public ResponseEntity<BookDetailsDto> findBookUsingIsbnCode(@PathVariable String isbnCode)
            throws BookNotFoundException {
        return new ResponseEntity<>(reviewService.getBook(isbnCode), HttpStatus.OK);
    }
}
