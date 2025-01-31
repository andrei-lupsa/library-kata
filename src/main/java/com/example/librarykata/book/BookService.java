package com.example.librarykata.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    public Book addBookToCatalog(Book book) {
        if (book.id() != null || book.borrowerId() != null) throw new IllegalArgumentException("Not a new book");
        return bookRepository.save(book);
    }
}
