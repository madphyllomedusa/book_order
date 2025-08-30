package ru.dynamika.book_order.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import ru.dynamika.book_order.dto.request.book.BookCreateRequest;
import ru.dynamika.book_order.dto.response.book.BookResponse;
import ru.dynamika.book_order.exception.BadRequestException;
import ru.dynamika.book_order.mapper.BookMapper;
import ru.dynamika.book_order.model.Book;
import ru.dynamika.book_order.repository.BookRepository;
import ru.dynamika.book_order.service.impl.BookServiceImpl;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    BookRepository bookRepository;
    @Mock
    BookMapper bookMapper;

    @InjectMocks
    BookServiceImpl service;

    @Captor
    ArgumentCaptor<Book> bookCaptor;

    static Stream<BookCreateRequest> validBooks() {
        return Stream.of(
                mk("Война и мир", "Лев Толстой", "9785389064965"),
                mk("Преступление и наказание", "Фёдор Достоевский", "9785699152027")
        );
    }

    private static BookCreateRequest mk(String title, String author, String isbn) {
        BookCreateRequest r = new BookCreateRequest();
        r.setTitle(title);
        r.setAuthor(author);
        r.setIsbn(isbn);
        return r;
    }

    @ParameterizedTest
    @MethodSource("validBooks")
    @DisplayName("create(): успешно создаёт книгу — UUID выставлен, поля сохранены")
    void create_ok(BookCreateRequest req) {
        Book entity = new Book();
        entity.setTitle(req.getTitle());
        entity.setAuthor(req.getAuthor());
        entity.setIsbn(req.getIsbn());
        when(bookMapper.fromCreateRequest(req)).thenReturn(entity);

        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> {
            Book b = inv.getArgument(0);
            if (b.getUuid() == null) {
                b.setUuid(UUID.randomUUID());
            }
            return b;
        });

        when(bookMapper.toResponse(any(Book.class))).thenAnswer(inv -> {
            Book b = inv.getArgument(0);
            BookResponse resp = new BookResponse();
            resp.setUuid(b.getUuid());
            resp.setTitle(b.getTitle());
            resp.setAuthor(b.getAuthor());
            resp.setIsbn(b.getIsbn());
            return resp;
        });

        BookResponse out = service.create(req);

        assertNotNull(out.getUuid(), "uuid должен быть установлен");
        assertEquals(req.getTitle(), out.getTitle());
        assertEquals(req.getAuthor(), out.getAuthor());
        assertEquals(req.getIsbn(), out.getIsbn());

        verify(bookRepository).save(bookCaptor.capture());
        Book savedArg = bookCaptor.getValue();
        assertEquals(req.getTitle(), savedArg.getTitle());
        assertEquals(req.getAuthor(), savedArg.getAuthor());
        assertEquals(req.getIsbn(), savedArg.getIsbn());

        verify(bookMapper, times(1)).fromCreateRequest(req);
        verify(bookMapper, times(1)).toResponse(any(Book.class));
    }

    @ParameterizedTest
    @MethodSource("validBooks")
    @DisplayName("create(): кидает BadRequestException при конфликте по ISBN")
    void create_conflict_isbn(BookCreateRequest req) {
        Book entity = new Book();
        when(bookMapper.fromCreateRequest(req)).thenReturn(entity);

        when(bookRepository.save(entity))
                .thenThrow(new DataIntegrityViolationException("duplicate key value violates unique constraint books_isbn_key"));

        BadRequestException ex = assertThrows(BadRequestException.class, () -> service.create(req));
        assertTrue(ex.getMessage().toLowerCase().contains("isbn"));
        verify(bookRepository, times(1)).save(entity);
        verify(bookMapper, never()).toResponse(any());
    }
}
