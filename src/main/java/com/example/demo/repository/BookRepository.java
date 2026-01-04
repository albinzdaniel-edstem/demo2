package com.example.demo.repository;

import com.example.demo.model.Book;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, UUID> {
    boolean existsBookByIsbnCode(String isbnCode);

    Optional<Book> findByIsbnCode(String isbnCode);
}
