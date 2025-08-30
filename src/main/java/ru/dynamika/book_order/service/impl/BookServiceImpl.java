package ru.dynamika.book_order.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.dynamika.book_order.dto.request.book.BookCreateRequest;
import ru.dynamika.book_order.dto.request.book.BookUpdateRequest;
import ru.dynamika.book_order.dto.response.book.BookResponse;
import ru.dynamika.book_order.exception.BadRequestException;
import ru.dynamika.book_order.exception.NotFoundException;
import ru.dynamika.book_order.mapper.BookMapper;
import ru.dynamika.book_order.model.Book;
import ru.dynamika.book_order.repository.BookRepository;
import ru.dynamika.book_order.service.BookService;

import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public Book findById(UUID id) {
        log.info("Find book by id {}", id);
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id " + id + " not found"));
    }

    @Override
    @Transactional
    public BookResponse create(BookCreateRequest request) {
        log.info("Create book {}", request);
        Book book = bookMapper.fromCreateRequest(request);
        save(book, request.getIsbn());
        log.info("Book created {}", book.getUuid().toString());
        return bookMapper.toResponse(book);
    }

    @Override
    @Transactional
    public BookResponse update(UUID bookUuid, BookUpdateRequest request) {
        log.info("Update book {}", bookUuid);
        Book book = findById(bookUuid);
        bookMapper.fromUpdateRequest(book, request);
        save(book, request.getIsbn());
        log.info("Book updated {}", book.getUuid().toString());
        return bookMapper.toResponse(book);
    }


    @Override
    public BookResponse get(UUID bookUuid) {
        return bookMapper.toResponse(findById(bookUuid));
    }


    @Override
    public Page<BookResponse> list(String title, String author, String isbn, Pageable pageable) {
        String searchByTitle = safeLike(title);
        String searchByAuthor = safeLike(author);
        String searchByIsbn = safeLike(isbn);

        Page<Book> page = bookRepository.search(searchByTitle, searchByAuthor, searchByIsbn, pageable);
        return page.map(bookMapper::toResponse);
    }

    private String safeLike(String s) {
        return (s == null || s.trim().isEmpty()) ? "%" : "%" + s.trim().toLowerCase() + "%";
    }

    private void save(Book book, String isbn) {
        try {
            bookRepository.save(book);
        } catch (Exception e) {
            throw new BadRequestException("Book with ISBN " + isbn + " already exists");
        }
    }
}
