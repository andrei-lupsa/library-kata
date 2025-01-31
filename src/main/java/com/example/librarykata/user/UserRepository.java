package com.example.librarykata.user;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserRepository {
    private final Map<Long, User> users;

    public UserRepository() {
        users = Arrays.stream(new User[]{
                new User(1L, "Alice", "Johnson"),
                new User(2L, "Bob", "Smith"),
                new User(3L, "Charlie", "Brown"),
                new User(4L, "David", "Miller"),
                new User(5L, "Emma", "Davis"),
                new User(6L, "Frank", "Wilson"),
                new User(7L, "Grace", "Anderson"),
                new User(8L, "Hannah", "Thomas"),
                new User(9L, "Isaac", "White"),
                new User(10L, "Jack", "Harris")
        }).collect(Collectors.toMap(User::id, user -> user));
    }

    public Optional<User> getUser(long userId) {
        return Optional.ofNullable(users.get(userId));
    }

}
