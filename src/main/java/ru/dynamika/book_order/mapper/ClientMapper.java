package ru.dynamika.book_order.mapper;

import org.springframework.stereotype.Component;
import ru.dynamika.book_order.dto.request.client.ClientCreateRequest;
import ru.dynamika.book_order.dto.request.client.ClientUpdateRequest;
import ru.dynamika.book_order.dto.response.client.ClientResponse;
import ru.dynamika.book_order.model.Client;

@Component
public class ClientMapper {
    public Client fromCreateRequest(ClientCreateRequest clientCreateRequest) {
        return Client.builder()
                .firstName(clientCreateRequest.getFirstName())
                .lastName(clientCreateRequest.getLastName())
                .middleName(clientCreateRequest.getMiddleName())
                .birthDate(clientCreateRequest.getBirthDate())
                .build();
    }

    public void fromUpdateRequest(Client client, ClientUpdateRequest clientUpdateRequest) {
        client.setBirthDate(clientUpdateRequest.getBirthDate());
        client.setFirstName(clientUpdateRequest.getFirstName());
        client.setLastName(clientUpdateRequest.getLastName());
        client.setMiddleName(clientUpdateRequest.getMiddleName());
    }

    public ClientResponse toResponse(Client client) {
        return ClientResponse.builder()
                .uuid(client.getUuid())
                .fullName(client.getFullName())
                .birthDate(client.getBirthDate())
                .build();
    }
}
