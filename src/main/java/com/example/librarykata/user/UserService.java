package com.example.librarykata.user;

import com.example.librarykata.book.Book;
import com.example.librarykata.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public void borrowBook(Long userId, Long bookId) {
        if(userRepository.getUser(userId).isEmpty()) throw new IllegalArgumentException("No user for id " + userId);
        bookRepository.borrowBook(bookId, userId);
    }

    public void returnBook(Long userId, Long bookId) {
        if(userRepository.getUser(userId).isEmpty()) throw new IllegalArgumentException("No user for id " + userId);
        bookRepository.returnBook(bookId, userId);
    }

    public BorrowedBooks getBorrowedBooks(Long userId) {
        Optional<User> user = userRepository.getUser(userId);
        if(user.isEmpty()) throw new IllegalArgumentException("No user for id " + userId);

        List<Book> books = bookRepository.findBorrowedBooks(userId);

        return new BorrowedBooks(user.get(), books);
    }

}
