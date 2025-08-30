package ru.dynamika.book_order.mapper;

import org.springframework.stereotype.Component;
import ru.dynamika.book_order.dto.request.book.BookCreateRequest;
import ru.dynamika.book_order.dto.request.book.BookUpdateRequest;
import ru.dynamika.book_order.dto.response.book.BookResponse;
import ru.dynamika.book_order.model.Book;


@Component
public class BookMapper {
    public Book fromCreateRequest(BookCreateRequest request) {
        return Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .build();
    }

    public void fromUpdateRequest(Book book, BookUpdateRequest request) {
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
    }

    public BookResponse toResponse(Book book) {
        return BookResponse.builder()
                .uuid(book.getUuid())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .build();
    }
}
