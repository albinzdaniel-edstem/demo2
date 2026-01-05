package com.example.demo.controller;

import com.example.demo.dto.*;
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
            @RequestBody CreateBookRequestDto request) {
        return new ResponseEntity<>(reviewService.createBook(request), HttpStatus.CREATED);
    }

    @GetMapping("/{isbnCode}")
    public ResponseEntity<BookDetailsDto> findBookUsingIsbnCode(@PathVariable String isbnCode) {
        return new ResponseEntity<>(reviewService.getBook(isbnCode), HttpStatus.OK);
    }

    @PutMapping("/{isbnCode}")
    public ResponseEntity<UpdateBookResponseDto> updateBook(
            @PathVariable String isbnCode, @RequestBody UpdateBookRequestDto request) {
        return new ResponseEntity<>(reviewService.updateBook(isbnCode, request), HttpStatus.OK);
    }
}
