package ru.dynamika.book_order.dto.request.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ApiModel(description = "Запрос на создание клиента")
public class ClientCreateRequest {

    @NotBlank
    @ApiModelProperty(value = "Фамилия", example = "Иванов", required = true)
    private String lastName;

    @NotBlank
    @ApiModelProperty(value = "Имя", example = "Иван", required = true)
    private String firstName;

    @ApiModelProperty(value = "Отчество", example = "Иванович")
    private String middleName;

    @NotNull
    @ApiModelProperty(value = "Дата рождения", example = "1990-05-01", required = true)
    private LocalDate birthDate;
}

