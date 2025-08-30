package ru.dynamika.book_order.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.dynamika.book_order.dto.request.book.BookCreateRequest;
import ru.dynamika.book_order.dto.request.book.BookUpdateRequest;
import ru.dynamika.book_order.dto.response.book.BookResponse;

import javax.validation.Valid;
import java.util.UUID;

@Api(tags = "Книги")
@Validated
@RequestMapping("/api/books")
public interface BookController {

    @ApiOperation(value = "Добавить книгу")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Книга создана"),
            @ApiResponse(code = 400, message = "Ошибка валидации"),
            @ApiResponse(code = 403, message = "Книга с таким ISBN уже существует")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    BookResponse create(@Valid @RequestBody BookCreateRequest request);

    @ApiOperation(value = "Получить книгу по UUID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Книга найдена"),
            @ApiResponse(code = 404, message = "Книга не найдена")
    })
    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    BookResponse get(@PathVariable UUID uuid);

    @ApiOperation(value = "Обновить книгу")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Книга обновлена"),
            @ApiResponse(code = 404, message = "Книга не найдена"),
            @ApiResponse(code = 403, message = "Книга с таким ISBN уже существует")
    })
    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    BookResponse update(@PathVariable UUID uuid, @Valid @RequestBody BookUpdateRequest request);

    @ApiOperation(value = "Список книг с фильтрами")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Номер страницы"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Размер страницы"),
            @ApiImplicitParam(name = "sort", dataType = "string", paramType = "query", value = "Сортировка, например: title,asc"),
            @ApiImplicitParam(name = "title", dataType = "string", paramType = "query", value = "Фильтр по названию"),
            @ApiImplicitParam(name = "author", dataType = "string", paramType = "query", value = "Фильтр по автору"),
            @ApiImplicitParam(name = "isbn", dataType = "string", paramType = "query", value = "Фильтр по ISBN")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<BookResponse> list(@RequestParam(required = false) String title,
                            @RequestParam(required = false) String author,
                            @RequestParam(required = false) String isbn,
                            Pageable pageable);

}

