package com.example.librarykata.book;

import lombok.With;

@With
public record Book(Long id, String title, String author, String isbn, int publicationYear, Long borrowerId) {
}
