package ru.dynamika.book_order.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.dynamika.book_order.dto.request.book.BookCreateRequest;
import ru.dynamika.book_order.dto.request.book.BookUpdateRequest;
import ru.dynamika.book_order.dto.response.book.BookResponse;
import ru.dynamika.book_order.model.Book;

import java.util.UUID;

public interface BookService {
    Book findById(UUID id);

    BookResponse create(BookCreateRequest request);

    BookResponse update(UUID bookUuid, BookUpdateRequest request);

    BookResponse get(UUID bookUuid);

    Page<BookResponse> list(String title,
                            String author,
                            String isbn,
                            Pageable pageable);
}
