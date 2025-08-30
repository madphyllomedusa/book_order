package ru.dynamika.book_order.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.dynamika.book_order.controller.BookController;
import ru.dynamika.book_order.dto.request.book.BookCreateRequest;
import ru.dynamika.book_order.dto.request.book.BookUpdateRequest;
import ru.dynamika.book_order.dto.response.book.BookResponse;
import ru.dynamika.book_order.repository.BookRepository;
import ru.dynamika.book_order.service.BookService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BookControllerImpl implements BookController {

    private final BookService bookService;

    @Override
    public BookResponse create(BookCreateRequest request) {
        return bookService.create(request);
    }

    @Override
    public BookResponse get(UUID uuid) {
        return bookService.get(uuid);
    }

    @Override
    public BookResponse update(UUID uuid, BookUpdateRequest request) {
        return bookService.update(uuid, request);
    }

    @Override
    public Page<BookResponse> list(String title, String author, String isbn, Pageable pageable) {
        return bookService.list(title, author, isbn, pageable);
    }
}
