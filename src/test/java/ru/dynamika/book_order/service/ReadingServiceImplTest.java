package ru.dynamika.book_order.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.dynamika.book_order.dto.request.reading.ReadingCreateRequest;
import ru.dynamika.book_order.dto.response.book.BookResponse;
import ru.dynamika.book_order.dto.response.client.ClientResponse;
import ru.dynamika.book_order.dto.response.reading.ReadingResponse;
import ru.dynamika.book_order.exception.NotFoundException;
import ru.dynamika.book_order.mapper.ReadingMapper;
import ru.dynamika.book_order.model.Book;
import ru.dynamika.book_order.model.Client;
import ru.dynamika.book_order.model.Reading;
import ru.dynamika.book_order.repository.ReadingRepository;
import ru.dynamika.book_order.service.impl.ReadingServiceImpl;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReadingServiceImplTest {

    @Mock
    ReadingRepository readingRepository;
    @Mock
    ReadingMapper readingMapper;
    @Mock
    ClientService clientService;
    @Mock
    BookService bookService;

    @InjectMocks
    ReadingServiceImpl service;


    static Stream<Arguments> validRequests() {
        UUID clientId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();

        ReadingCreateRequest withTakenAt = new ReadingCreateRequest();
        withTakenAt.setClientUuid(clientId);
        withTakenAt.setBookUuid(bookId);

        ReadingCreateRequest withoutTakenAt = new ReadingCreateRequest();
        withoutTakenAt.setClientUuid(clientId);
        withoutTakenAt.setBookUuid(bookId);

        return Stream.of(
                Arguments.of("с takenAt в запросе", withTakenAt),
                Arguments.of("без takenAt (ставим now())", withoutTakenAt)
        );
    }

    @ParameterizedTest(name = "create(): успешно создаёт {0}")
    @MethodSource("validRequests")
    void create_ok(String caseName, ReadingCreateRequest req) {
        UUID clientId = req.getClientUuid();
        UUID bookId = req.getBookUuid();

        Client client = new Client();
        client.setUuid(clientId);
        client.setFirstName("Иван");
        client.setLastName("Иванов");

        Book book = new Book();
        book.setUuid(bookId);
        book.setTitle("Война и мир");

        when(clientService.findById(clientId)).thenReturn(client);
        when(bookService.findById(bookId)).thenReturn(book);

        when(readingRepository.save(any(Reading.class))).thenAnswer(inv -> {
            Reading r = inv.getArgument(0);
            assertNotNull(r.getClient());
            assertNotNull(r.getBook());
            assertNotNull(r.getTakenAt(), "takenAt должен быть установлен сервисом");
            if (r.getUuid() == null) r.setUuid(UUID.randomUUID());
            return r;
        });

        when(readingMapper.toResponse(any(Reading.class))).thenAnswer(inv -> {
            Reading r = inv.getArgument(0);
            ReadingResponse resp = new ReadingResponse();
            resp.setUuid(r.getUuid());
            resp.setTakenAt(OffsetDateTime.now());
            resp.setClient(new ClientResponse());
            resp.setBook(new BookResponse());
            return resp;
        });

        ReadingResponse out = service.create(req);

        assertNotNull(out);
        assertNotNull(out.getUuid());
        verify(clientService, times(1)).findById(clientId);
        verify(bookService, times(1)).findById(bookId);
        verify(readingRepository, times(1)).save(any(Reading.class));
        verify(readingMapper, times(1)).toResponse(any(Reading.class));
    }

    enum Missing {CLIENT, BOOK}

    static Stream<Arguments> notFoundCases() {
        return Stream.of(
                Arguments.of(Missing.CLIENT),
                Arguments.of(Missing.BOOK)
        );
    }

    @ParameterizedTest(name = "create(): NotFound если отсутствует {0}")
    @MethodSource("notFoundCases")
    @DisplayName("create(): NotFound при отсутствии клиента/книги")
    void create_not_found(Missing missing) {
        UUID clientId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();

        ReadingCreateRequest req = new ReadingCreateRequest();
        req.setClientUuid(clientId);
        req.setBookUuid(bookId);

        if (missing == Missing.CLIENT) {
            when(clientService.findById(clientId)).thenThrow(new NotFoundException("Client not found"));
        } else {
            when(clientService.findById(clientId)).thenReturn(new Client());
            when(bookService.findById(bookId)).thenThrow(new NotFoundException("Book not found"));
        }

        assertThrows(NotFoundException.class, () -> service.create(req));

        if (missing == Missing.CLIENT) {
            verify(bookService, never()).findById(any());
        }
        verify(readingRepository, never()).save(any());
        verify(readingMapper, never()).toResponse(any());
    }
}
