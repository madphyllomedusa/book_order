package ru.dynamika.book_order.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.dynamika.book_order.controller.ClientController;
import ru.dynamika.book_order.dto.request.client.ClientCreateRequest;
import ru.dynamika.book_order.dto.request.client.ClientUpdateRequest;
import ru.dynamika.book_order.dto.response.client.ClientResponse;
import ru.dynamika.book_order.service.ClientService;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ClientControllerImpl implements ClientController {

    private final ClientService clientService;


    @Override
    public ClientResponse create(ClientCreateRequest request) {
        return clientService.create(request);
    }

    @Override
    public ClientResponse get(UUID uuid) {
        return clientService.get(uuid);
    }

    @Override
    public ClientResponse update(UUID uuid, ClientUpdateRequest request) {
        return clientService.update(uuid, request);
    }

    @Override
    public Page<ClientResponse> list(String lastName, String firstName, LocalDate birthDateFrom, LocalDate birthDateTo, Pageable pageable) {
        return clientService.list(lastName, firstName, birthDateFrom, birthDateTo, pageable);
    }
}
