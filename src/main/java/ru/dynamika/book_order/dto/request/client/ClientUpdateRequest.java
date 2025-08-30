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
@ApiModel(description = "Запрос на обновление клиента")
public class ClientUpdateRequest {

    @NotBlank
    @ApiModelProperty(value = "Фамилия", example = "Петров", required = true)
    private String lastName;

    @NotBlank
    @ApiModelProperty(value = "Имя", example = "Пётр", required = true)
    private String firstName;

    @ApiModelProperty(value = "Отчество", example = "Сергеевич")
    private String middleName;

    @NotNull
    @ApiModelProperty(value = "Дата рождения", example = "1992-07-15", required = true)
    private LocalDate birthDate;
}

