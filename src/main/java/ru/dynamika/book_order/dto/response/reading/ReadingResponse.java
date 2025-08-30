package ru.dynamika.book_order.dto.response.reading;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dynamika.book_order.dto.response.book.BookResponse;
import ru.dynamika.book_order.dto.response.client.ClientResponse;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Информация о чтении (выдаче книги клиенту)")
public class ReadingResponse {

    @ApiModelProperty(value = "UUID записи о чтении", example = "33333333-3333-3333-3333-333333333333")
    private UUID uuid;

    @ApiModelProperty(value = "Клиент, который взял книгу")
    private ClientResponse client;

    @ApiModelProperty(value = "Книга, которая была взята")
    private BookResponse book;

    @ApiModelProperty(value = "Дата и время, когда книга была взята", example = "2025-08-30T12:00:00Z")
    private OffsetDateTime takenAt;
}
