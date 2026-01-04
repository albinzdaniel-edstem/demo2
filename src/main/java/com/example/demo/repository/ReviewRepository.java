package com.example.demo.repository;

import com.example.demo.model.Review;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByBook_IsbnCode(String bookIsbnCode);
}
