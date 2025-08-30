package ru.dynamika.book_order.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.dynamika.book_order.dto.request.client.ClientCreateRequest;
import ru.dynamika.book_order.dto.request.client.ClientUpdateRequest;
import ru.dynamika.book_order.dto.response.client.ClientResponse;
import ru.dynamika.book_order.model.Client;

import java.time.LocalDate;
import java.util.UUID;

public interface ClientService {
    Client findById(UUID id);

    ClientResponse create(ClientCreateRequest request);

    ClientResponse update(UUID clientUuid, ClientUpdateRequest request);

    ClientResponse get(UUID clientUuid);

    Page<ClientResponse> list(String lastName,
                              String firstName,
                              LocalDate birthDateFrom,
                              LocalDate birthDateTo,
                              Pageable pageable);
}
