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
import ru.dynamika.book_order.dto.request.client.ClientCreateRequest;
import ru.dynamika.book_order.dto.request.client.ClientUpdateRequest;
import ru.dynamika.book_order.dto.response.client.ClientResponse;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.UUID;

@Api(tags = "Клиенты")
@Validated
@RequestMapping("/api/clients")
public interface ClientController {

    @ApiOperation("Добавить клиента")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Клиент создан"),
            @ApiResponse(code = 400, message = "Ошибка валидации")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ClientResponse create(@Valid @RequestBody ClientCreateRequest request);

    @ApiOperation("Получить клиента по UUID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Клиент найден"),
            @ApiResponse(code = 404, message = "Клиент не найден")
    })
    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    ClientResponse get(@PathVariable UUID uuid);

    @ApiOperation("Обновить клиента")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Клиент обновлён"),
            @ApiResponse(code = 404, message = "Клиент не найден")
    })
    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    ClientResponse update(@PathVariable UUID uuid, @Valid @RequestBody ClientUpdateRequest request);

    @ApiOperation("Список клиентов с фильтрами")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Номер страницы"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Размер страницы"),
            @ApiImplicitParam(name = "sort", dataType = "string", paramType = "query", value = "Сортировка, например: lastName,asc"),
            @ApiImplicitParam(name = "lastName", dataType = "string", paramType = "query", value = "Фильтр по фамилии"),
            @ApiImplicitParam(name = "firstName", dataType = "string", paramType = "query", value = "Фильтр по имени"),
            @ApiImplicitParam(name = "birthDateFrom", dataType = "string", paramType = "query", value = "Дата рождения от (yyyy-MM-dd)"),
            @ApiImplicitParam(name = "birthDateTo", dataType = "string", paramType = "query", value = "Дата рождения до (yyyy-MM-dd)")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<ClientResponse> list(@RequestParam(required = false) String lastName,
                              @RequestParam(required = false) String firstName,
                              @RequestParam(required = false) LocalDate birthDateFrom,
                              @RequestParam(required = false) LocalDate birthDateTo,
                              Pageable pageable);
}

