package ru.dynamika.book_order.dto.request.book;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ApiModel(description = "Запрос на создание книги")
public class BookCreateRequest {
    @NotBlank(message = "Название книги не может быть пустым")
    @Size(max = 255)
    @ApiModelProperty(value = "Название книги", example = "Война и мир", required = true)
    private String title;

    @NotBlank(message = "Автор книги не может быть пустым")
    @Size(max = 255)
    @ApiModelProperty(value = "Автор книги", example = "Лев Толстой", required = true)
    private String author;

    @NotBlank(message = "Идентификатор книги не может быть пустым")
    @Size(max = 20)
    @ApiModelProperty(value = "ISBN книги", example = "9785389064965", required = true)
    private String isbn;
}
