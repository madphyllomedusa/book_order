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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.dynamika.book_order.dto.request.reading.ReadingCreateRequest;
import ru.dynamika.book_order.dto.response.reading.ReadingResponse;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.UUID;


@Api(tags = "Чтения")
@Validated
@RequestMapping("/api/readings")
public interface ReadingController {

    @ApiOperation("Выдать книгу клиенту (создать запись о чтении)")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Чтение создано"),
            @ApiResponse(code = 400, message = "Ошибка валидации"),
            @ApiResponse(code = 404, message = "Клиент или книга не найдены")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ReadingResponse create(@Valid @RequestBody ReadingCreateRequest request);

    @ApiOperation("Получить запись о чтении по UUID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Запись найдена"),
            @ApiResponse(code = 404, message = "Запись не найдена")
    })
    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    ReadingResponse get(@PathVariable UUID uuid);

    @ApiOperation("Список чтений с фильтрами")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Номер страницы"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Размер страницы"),
            @ApiImplicitParam(name = "sort", dataType = "string", paramType = "query", value = "Сортировка, например: takenAt,desc"),
            @ApiImplicitParam(name = "clientUuid", dataType = "string", paramType = "query", value = "Фильтр по клиенту"),
            @ApiImplicitParam(name = "bookUuid", dataType = "string", paramType = "query", value = "Фильтр по книге"),
            @ApiImplicitParam(name = "from", dataType = "string", paramType = "query", value = "Дата начала периода (ISO8601)"),
            @ApiImplicitParam(name = "to", dataType = "string", paramType = "query", value = "Дата окончания периода (ISO8601)")
    })
    @ApiResponse(code = 200, message = "Вывод записей")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<ReadingResponse> list(@RequestParam(required = false) UUID clientUuid,
                               @RequestParam(required = false) UUID bookUuid,
                               @RequestParam(required = false) OffsetDateTime from,
                               @RequestParam(required = false) OffsetDateTime to,
                               Pageable pageable);

    @ApiOperation("Список чтений по клиенту")
    @ApiResponse(code = 200, message = "Вывод записей")
    @GetMapping("/by-client/{clientUuid}")
    @ResponseStatus(HttpStatus.OK)
    Page<ReadingResponse> listByClient(@PathVariable UUID clientUuid, Pageable pageable);

    @ApiOperation("Список чтений по книге")
    @ApiResponse(code = 200, message = "Вывод записей")
    @GetMapping("/by-book/{bookUuid}")
    @ResponseStatus(HttpStatus.OK)
    Page<ReadingResponse> listByBook(@PathVariable UUID bookUuid, Pageable pageable);
}

