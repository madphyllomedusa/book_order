package ru.dynamika.book_order.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.dynamika.book_order.dto.request.reading.ReadingCreateRequest;
import ru.dynamika.book_order.dto.response.reading.ReadingResponse;
import ru.dynamika.book_order.exception.NotFoundException;
import ru.dynamika.book_order.mapper.ReadingMapper;
import ru.dynamika.book_order.model.Book;
import ru.dynamika.book_order.model.Client;
import ru.dynamika.book_order.model.Reading;
import ru.dynamika.book_order.repository.ReadingRepository;
import ru.dynamika.book_order.service.BookService;
import ru.dynamika.book_order.service.ClientService;
import ru.dynamika.book_order.service.ReadingService;

import java.time.OffsetDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadingServiceImpl implements ReadingService {

    private final ReadingRepository readingRepository;
    private final BookService bookService;
    private final ClientService clientService;
    private final ReadingMapper readingMapper;

    @Override
    public ReadingResponse create(ReadingCreateRequest request) {
        log.info("Create Reading request: {}", request);
        Client client = clientService.findById(request.getClientUuid());
        Book book = bookService.findById(request.getBookUuid());

        Reading reading = Reading.builder()
                .takenAt(OffsetDateTime.now())
                .client(client)
                .book(book)
                .build();

        readingRepository.save(reading);

        log.info("Reading created {}", reading.getUuid());
        return readingMapper.toResponse(reading);
    }


    @Override
    public ReadingResponse get(UUID readingUuid) {
        log.info("Get reading {}", readingUuid);
        Reading reading = readingRepository.findById(readingUuid)
                .orElseThrow(() -> new NotFoundException("Reading with id " + readingUuid + " not found"));
        return readingMapper.toResponse(reading);
    }


    @Override
    public Page<ReadingResponse> list(UUID clientUuid,
                                      UUID bookUuid,
                                      OffsetDateTime from,
                                      OffsetDateTime to,
                                      Pageable pageable) {
        log.info("List readings by filters: client={}, book={}, from={}, to={}", clientUuid, bookUuid, from, to);
        Specification<Reading> spec = Specification.where(null);

        if (clientUuid != null) {
            spec = spec.and((root, q, cb) -> cb.equal(root.get("client").get("uuid"), clientUuid));
        }
        if (bookUuid != null) {
            spec = spec.and((root, q, cb) -> cb.equal(root.get("book").get("uuid"), bookUuid));
        }
        if (from != null) {
            spec = spec.and((root, q, cb) -> cb.greaterThanOrEqualTo(root.get("takenAt"), from.toLocalDateTime()));
        }
        if (to != null) {
            spec = spec.and((root, q, cb) -> cb.lessThanOrEqualTo(root.get("takenAt"), to.toLocalDateTime()));
        }

        Page<Reading> page = readingRepository.findAll(spec, pageable);
        return page.map(readingMapper::toResponse);
    }


    @Override
    public Page<ReadingResponse> listByClient(UUID clientUuid, Pageable pageable) {
        log.info("List readings by client {}", clientUuid);
        Page<Reading> page = readingRepository.findAllByClientUuid(clientUuid, pageable);
        return page.map(readingMapper::toResponse);
    }


    @Override
    public Page<ReadingResponse> listByBook(UUID bookUuid, Pageable pageable) {
        log.info("List readings by book {}", bookUuid);
        Page<Reading> page = readingRepository.findAllByBookUuid(bookUuid, pageable);
        return page.map(readingMapper::toResponse);
    }
}
