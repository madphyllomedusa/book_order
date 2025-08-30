package ru.dynamika.book_order.dto.request.reading;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@ApiModel(description = "Запрос на создание записи о чтении (клиент берёт книгу)")
public class ReadingCreateRequest {

    @NotNull
    @ApiModelProperty(value = "UUID клиента", example = "11111111-1111-1111-1111-111111111111", required = true)
    private UUID clientUuid;

    @NotNull
    @ApiModelProperty(value = "UUID книги", example = "22222222-2222-2222-2222-222222222222", required = true)
    private UUID bookUuid;

}
