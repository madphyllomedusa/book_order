package ru.dynamika.book_order.dto.response.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Информация о клиенте")
public class ClientResponse {

    @ApiModelProperty(value = "UUID клиента", example = "11111111-1111-1111-1111-111111111111")
    private UUID uuid;

    @ApiModelProperty(value = "ФИО клиента", example = "Иванов Иван Иванович")
    private String fullName;

    @ApiModelProperty(value = "Дата рождения", example = "1990-05-01")
    private LocalDate birthDate;
}
