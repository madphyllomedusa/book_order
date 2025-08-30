package ru.dynamika.book_order.dto.request.book;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ApiModel(description = "Запрос на обновление книги")
public class BookUpdateRequest {

    @NotBlank
    @Size(max = 255)
    @ApiModelProperty(value = "Название книги", example = "Война и мир. Том 1", required = true)
    private String title;

    @NotBlank
    @Size(max = 255)
    @ApiModelProperty(value = "Автор книги", example = "Лев Толстой", required = true)
    private String author;

    @NotBlank
    @Size(max = 20)
    @ApiModelProperty(value = "ISBN книги", example = "9785389064965", required = true)
    private String isbn;
}
