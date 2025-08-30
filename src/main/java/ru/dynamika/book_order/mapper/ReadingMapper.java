package ru.dynamika.book_order.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dynamika.book_order.dto.response.reading.ReadingResponse;
import ru.dynamika.book_order.model.Reading;

@Component
@RequiredArgsConstructor
public class ReadingMapper {
    private final BookMapper bookMapper;
    private final ClientMapper clientMapper;

    public ReadingResponse toResponse(Reading reading) {
        return ReadingResponse.builder()
                .uuid(reading.getUuid())
                .book(bookMapper.toResponse(reading.getBook()))
                .client(clientMapper.toResponse(reading.getClient()))
                .takenAt(reading.getTakenAt())
                .build();
    }
}
