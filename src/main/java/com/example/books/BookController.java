package com.example.books;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final ConcurrentHashMap<Long, Book> books = new ConcurrentHashMap<>();
    private final AtomicLong ids = new AtomicLong();

    public BookController() {
        addSeed("The DevOps Handbook", "Gene Kim");
        addSeed("Clean Code", "Robert C. Martin");
    }

    private void addSeed(String title, String author) {
        long id = ids.incrementAndGet();
        books.put(id, new Book(id, title, author));
    }

    @GetMapping
    public List<Book> getAll() {
        return books.values().stream().sorted(Comparator.comparing(Book::id)).toList();
    }

    @GetMapping("/{id}")
    public Book getOne(@PathVariable long id) {
        Book book = books.get(id);
        if (book == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        return book;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@Valid @RequestBody Book request) {
        long id = ids.incrementAndGet();
        Book book = new Book(id, request.title(), request.author());
        books.put(id, book);
        return book;
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable long id, @Valid @RequestBody Book request) {
        if (!books.containsKey(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        Book updated = new Book(id, request.title(), request.author());
        books.put(id, updated);
        return updated;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        if (books.remove(id) == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
    }
}
