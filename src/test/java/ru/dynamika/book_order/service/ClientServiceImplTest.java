package ru.dynamika.book_order.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import ru.dynamika.book_order.dto.request.client.ClientCreateRequest;
import ru.dynamika.book_order.dto.response.client.ClientResponse;
import ru.dynamika.book_order.exception.BadRequestException;
import ru.dynamika.book_order.mapper.ClientMapper;
import ru.dynamika.book_order.model.Client;
import ru.dynamika.book_order.repository.ClientRepository;
import ru.dynamika.book_order.service.impl.ClientServiceImpl;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    ClientRepository clientRepository;
    @Mock
    ClientMapper clientMapper;

    @InjectMocks
    ClientServiceImpl service;

    @Captor
    ArgumentCaptor<Client> clientCaptor;

    static Stream<ClientCreateRequest> validClients() {
        return Stream.of(
                mk("Иванов", "Иван", "Иванович", LocalDate.of(1990, 5, 1)),
                mk("Петров", "Пётр", null, LocalDate.of(1985, 3, 12))
        );
    }

    private static ClientCreateRequest mk(String last, String first, String mid, LocalDate dob) {
        ClientCreateRequest r = new ClientCreateRequest();
        r.setLastName(last);
        r.setFirstName(first);
        r.setMiddleName(mid);
        r.setBirthDate(dob);
        return r;
    }

    @ParameterizedTest
    @MethodSource("validClients")
    @DisplayName("Client.create(): ok — UUID выставлен, поля сохранены")
    void create_ok(ClientCreateRequest req) {
        // 1) DTO -> Entity
        Client entity = new Client();
        entity.setLastName(req.getLastName());
        entity.setFirstName(req.getFirstName());
        entity.setMiddleName(req.getMiddleName());
        entity.setBirthDate(req.getBirthDate());
        when(clientMapper.fromCreateRequest(req)).thenReturn(entity);

        // 2) save возвращает ТУ ЖЕ сущность с выставленным uuid
        when(clientRepository.save(any(Client.class))).thenAnswer(inv -> {
            Client c = inv.getArgument(0);
            if (c.getUuid() == null) c.setUuid(UUID.randomUUID());
            return c;
        });

        // 3) Entity -> DTO
        when(clientMapper.toResponse(any(Client.class))).thenAnswer(inv -> {
            Client c = inv.getArgument(0);
            ClientResponse resp = new ClientResponse();
            resp.setUuid(c.getUuid());
            resp.setFullName(c.getFullName());
            resp.setBirthDate(c.getBirthDate());
            resp.setFullName(c.getFullName());
            return resp;
        });

        ClientResponse out = service.create(req);

        assertNotNull(out.getUuid());
        assertEquals(req.getBirthDate(), out.getBirthDate());

        verify(clientRepository).save(clientCaptor.capture());
        Client savedArg = clientCaptor.getValue();
        assertEquals(req.getLastName(), savedArg.getLastName());
        assertEquals(req.getFirstName(), savedArg.getFirstName());
        assertEquals(req.getMiddleName(), savedArg.getMiddleName());
        assertEquals(req.getBirthDate(), savedArg.getBirthDate());
    }

    @ParameterizedTest
    @MethodSource("validClients")
    @DisplayName("Client.create(): BadRequest при конфликте (например, уникальные ограничения)")
    void create_conflict(ClientCreateRequest req) {
        Client entity = new Client();
        when(clientMapper.fromCreateRequest(req)).thenReturn(entity);

        when(clientRepository.save(entity))
                .thenThrow(new DataIntegrityViolationException("duplicate"));

        assertThrows(BadRequestException.class, () -> service.create(req));
        verify(clientMapper, never()).toResponse(any());
    }
}
