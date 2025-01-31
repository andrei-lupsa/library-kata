package com.example.librarykata.user;

import com.example.librarykata.book.Book;

import java.util.List;

public record BorrowedBooks(User user, List<Book> borrowedBooks) {
}
