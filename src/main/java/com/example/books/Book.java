package com.example.books;

import jakarta.validation.constraints.NotBlank;

public record Book(
        Long id,
        @NotBlank(message = "title is required") String title,
        @NotBlank(message = "author is required") String author
) {}
