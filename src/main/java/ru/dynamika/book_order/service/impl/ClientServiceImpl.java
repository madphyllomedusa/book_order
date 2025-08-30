package ru.dynamika.book_order.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.dynamika.book_order.dto.request.client.ClientCreateRequest;
import ru.dynamika.book_order.dto.request.client.ClientUpdateRequest;
import ru.dynamika.book_order.dto.response.client.ClientResponse;
import ru.dynamika.book_order.exception.BadRequestException;
import ru.dynamika.book_order.exception.NotFoundException;
import ru.dynamika.book_order.mapper.ClientMapper;
import ru.dynamika.book_order.model.Client;
import ru.dynamika.book_order.repository.ClientRepository;
import ru.dynamika.book_order.service.ClientService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public Client findById(UUID id) {
        log.info("Find client by id {}", id);
        return clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client with id " + id + " not found"));
    }

    @Override
    @Transactional
    public ClientResponse create(ClientCreateRequest request) {
        log.info("Create client {}", request);
        Client client = clientMapper.fromCreateRequest(request);
        save(client, request.getLastName(), request.getFirstName());
        log.info("Client created {}", client.getUuid().toString());
        return clientMapper.toResponse(client);
    }

    @Override
    @Transactional
    public ClientResponse update(UUID clientUuid, ClientUpdateRequest request) {
        log.info("Update client {}", clientUuid);
        Client client = findById(clientUuid);
        clientMapper.fromUpdateRequest(client, request);
        save(client, request.getLastName(), request.getFirstName());
        log.info("Client updated {}", client.getUuid().toString());
        return clientMapper.toResponse(client);
    }

    @Override
    public ClientResponse get(UUID clientUuid) {
        return clientMapper.toResponse(findById(clientUuid));
    }

    @Override
    public Page<ClientResponse> list(String lastName,
                                     String firstName,
                                     LocalDate birthDateFrom,
                                     LocalDate birthDateTo,
                                     Pageable pageable) {
        String searchByLast = safeLike(lastName);
        String searchByFirst = safeLike(firstName);

        Page<Client> page = clientRepository.search(searchByLast, searchByFirst, birthDateFrom, birthDateTo, pageable);
        return page.map(clientMapper::toResponse);
    }

    private String safeLike(String s) {
        return (s == null || s.trim().isEmpty()) ? "%" : "%" + s.trim().toLowerCase() + "%";
    }

    private void save(Client client, String lastName, String firstName) {
        try {
            clientRepository.save(client);
        } catch (Exception e) {
            throw new BadRequestException("Client with name " + lastName + " " + firstName + " already exists");
        }
    }
}

