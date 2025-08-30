package ru.dynamika.book_order.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.dynamika.book_order.controller.ReadingController;
import ru.dynamika.book_order.dto.request.reading.ReadingCreateRequest;
import ru.dynamika.book_order.dto.response.reading.ReadingResponse;
import ru.dynamika.book_order.service.ReadingService;

import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ReadingControllerImpl implements ReadingController {

    private final ReadingService readingService;


    @Override
    public ReadingResponse create(ReadingCreateRequest request) {
        return readingService.create(request);
    }

    @Override
    public ReadingResponse get(UUID uuid) {
        return readingService.get(uuid);
    }

    @Override
    public Page<ReadingResponse> list(UUID clientUuid, UUID bookUuid, OffsetDateTime from, OffsetDateTime to, Pageable pageable) {
        return readingService.list(clientUuid, bookUuid, from, to, pageable);
    }

    @Override
    public Page<ReadingResponse> listByClient(UUID clientUuid, Pageable pageable) {
        return readingService.listByClient(clientUuid, pageable);
    }

    @Override
    public Page<ReadingResponse> listByBook(UUID bookUuid, Pageable pageable) {
        return readingService.listByBook(bookUuid, pageable);
    }
}
