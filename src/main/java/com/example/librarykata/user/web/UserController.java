package com.example.librarykata.user.web;

import com.example.librarykata.user.BorrowedBooks;
import com.example.librarykata.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @PostMapping("{userId}/borrow/{bookId}")
    public void borrowBook(@PathVariable Long userId, @PathVariable Long bookId) {
        userService.borrowBook(userId, bookId);
    }

    @PostMapping("{userId}/return/{bookId}")
    public void returnBook(@PathVariable Long userId, @PathVariable Long bookId) {
        userService.returnBook(userId, bookId);
    }

    @GetMapping("{userId}/borrowed-books")
    public BorrowedBooks borrowBooks(@PathVariable Long userId) {
        return userService.getBorrowedBooks(userId);
    }
}
