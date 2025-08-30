package ru.dynamika.book_order.dto.response.book;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Информация о книге")
public class BookResponse {

    @ApiModelProperty(value = "UUID книги", example = "22222222-2222-2222-2222-222222222222")
    private UUID uuid;

    @ApiModelProperty(value = "Название книги", example = "Война и мир")
    private String title;

    @ApiModelProperty(value = "Автор книги", example = "Лев Толстой")
    private String author;

    @ApiModelProperty(value = "ISBN книги", example = "9785389064965")
    private String isbn;
}
