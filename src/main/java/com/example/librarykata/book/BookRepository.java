package com.example.librarykata.book;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

//just a simple in memory repository,
// in real life should be a spring-data-jpa or spring-data-jdbc repository
@Component
public class BookRepository {
    private final ConcurrentMap<Long, Book> books;
    private AtomicLong nextId = new AtomicLong(0);

    public BookRepository() {
        books = Arrays.stream(new Book[]{
                new Book(1L, "To Kill a Mockingbird", "Harper Lee", "9780061120084", 1960, null),
                new Book(2L, "1984", "George Orwell", "9780451524935", 1949, null),
                new Book(3L, "The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", 1925, null),
                new Book(4L, "Pride and Prejudice", "Jane Austen", "9781503290563", 1813, null),
                new Book(5L, "The Catcher in the Rye", "J.D. Salinger", "9780316769488", 1951, null),
                new Book(6L, "Moby-Dick", "Herman Melville", "9781503280786", 1851, null),
                new Book(7L, "War and Peace", "Leo Tolstoy", "9781420959711", 1869, null),
                new Book(8L, "The Hobbit", "J.R.R. Tolkien", "9780547928227", 1937, null),
                new Book(9L, "Brave New World", "Aldous Huxley", "9780060850524", 1932, null),
                new Book(10L, "Crime and Punishment", "Fyodor Dostoevsky", "9780486415871", 1866, null)
        }).collect(Collectors.toConcurrentMap(Book::id, book -> book));
        nextId.set(11);
    }

    public Book save(Book book) {
        long newBookId = nextId.getAndIncrement();
        Book newBook = book.withId(newBookId);
        books.put(newBookId, newBook);
        return newBook;
    }

    public void borrowBook(Long bookId, Long userId) {
        Book book = books.get(bookId);
        if (book == null) throw new RuntimeException("Book not found");
        if (book.borrowerId() != null) throw new RuntimeException("Book borrowed by another user");
        Book borrowedBook = book.withBorrowerId(userId);
        if (!books.replace(bookId, book, borrowedBook)) throw new RuntimeException("Error borrowing book");
    }

    public void returnBook(Long bookId, Long userId) {
        Book book = books.get(bookId);
        if (book == null) throw new RuntimeException("Book not found");
        if (!userId.equals(book.borrowerId())) throw new RuntimeException("Book is not borrowed by current user");
        Book returnedBook = book.withBorrowerId(null);
        if (!books.replace(bookId, book, returnedBook)) throw new RuntimeException("Error returning book");
    }

    public List<Book> findBorrowedBooks(Long userId) {
        return books.values().stream().filter(book -> userId.equals(book.borrowerId())).toList();
    }

}
