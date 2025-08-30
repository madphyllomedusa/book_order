package ru.dynamika.book_order.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.dynamika.book_order.dto.request.reading.ReadingCreateRequest;
import ru.dynamika.book_order.dto.response.reading.ReadingResponse;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface ReadingService {

    ReadingResponse create(ReadingCreateRequest request);

    ReadingResponse get(UUID readingUuid);

    Page<ReadingResponse> list(UUID clientUuid,
                               UUID bookUuid,
                               OffsetDateTime from,
                               OffsetDateTime to,
                               Pageable pageable);

    Page<ReadingResponse> listByClient(UUID clientUuid, Pageable pageable);

    Page<ReadingResponse> listByBook(UUID bookUuid, Pageable pageable);
}
