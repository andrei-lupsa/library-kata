package com.example.librarykata;

import com.example.librarykata.book.Book;
import com.example.librarykata.user.BorrowedBooks;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LibraryKataApplicationTests {

    @Autowired
    private WebTestClient client;

    @Test
    void testCreateBook() {
        Book newBook = new Book(null,
                "The Lord of the Rings",
                "J.R.R. Tolkien",
                "9780261102385",
                1954,
                null);
        client
                .post()
                .uri("/books")
                .bodyValue(newBook)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void testBorrowBook() {
        client
                .post()
                .uri("/users/{userId}/borrow/{bookId}", 1, 1)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testReturnBook() {
        client
                .post()
                .uri("/users/{userId}/borrow/{bookId}", 1, 2)
                .exchange()
                .expectStatus().isOk();

        client
                .post()
                .uri("/users/{userId}/return/{bookId}", 1, 2)
                .exchange()
                .expectStatus().isOk();
    }


    @Test
    void testGetBorrowedBooks() {
        client
                .post()
                .uri("/users/{userId}/borrow/{bookId}", 2, 3)
                .exchange()
                .expectStatus().isOk();

        client
                .post()
                .uri("/users/{userId}/borrow/{bookId}", 2, 4)
                .exchange()
                .expectStatus().isOk();

        client
                .get()
                .uri("/users/{userId}/borrowed-books", 2)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BorrowedBooks.class).value(borrowedBooks -> {
                    assertThat(borrowedBooks.user().firstName()).isEqualTo("Bob");
                    assertThat(borrowedBooks.borrowedBooks().size()).isEqualTo(2);
                });
    }

}
