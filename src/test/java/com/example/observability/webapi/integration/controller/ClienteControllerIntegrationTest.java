package com.example.observability.webapi.integration.controller;

import com.example.observability.domain.entities.Cliente;
import com.example.observability.infrastructure.persistence.entity.ClienteEntity;
import com.example.observability.infrastructure.persistence.entity.StatusCliente;
import com.example.observability.infrastructure.persistence.repository.ClienteRepository;
import com.example.observability.webapi.representation.ClienteRepresentation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ClienteRepository clienteRepository;

    private ClienteEntity clienteEntityMock;

    private ClienteRepresentation clienteRepresentationMock;

    private List<ClienteRepresentation> clienteRepresentationListMock;

    @BeforeEach
    void init() {
        clienteEntityMock = new EasyRandom().nextObject(ClienteEntity.class);
        clienteEntityMock.setStatusCliente(StatusCliente.Ativo);
        Mockito.when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteEntityMock));
        clienteRepresentationMock = new ClienteRepresentation(clienteEntityMock.toDomain());

        List<ClienteEntity> clienteEntityListMock = new EasyRandom().objects(ClienteEntity.class, 15).collect(Collectors.toList());
        Mockito.when(clienteRepository.findAll()).thenReturn(clienteEntityListMock);
        List<Cliente> clientesMock = clienteEntityListMock.stream().map(cliente -> cliente.toDomain()).toList();
        clienteRepresentationListMock = clientesMock.stream().map(ClienteRepresentation::new).toList();
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void getCliente_success() throws Exception {
        mockMvc.perform(get("/api/cliente/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content().string(
                                new ObjectMapper().writeValueAsString(clienteRepresentationMock)
                        )
                );
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void getCliente_notFound() throws Exception {
        mockMvc.perform(get("/api/cliente/112")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void ClienteController_unauthorized() throws Exception {
        mockMvc.perform(get("/api/cliente/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/cliente")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/cliente/disable/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void getAllClientes_success() throws Exception {
        mockMvc.perform(get("/api/cliente")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content().string(
                                new ObjectMapper().writeValueAsString(clienteRepresentationListMock)
                        )
                );
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void disableCliente_success() throws Exception {
        ClienteEntity clienteEntityDisabled = new EasyRandom().nextObject(ClienteEntity.class);
        clienteEntityDisabled.setStatusCliente(StatusCliente.Inativo);
        Mockito.when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(clienteEntityDisabled);
        clienteRepresentationMock.setStatusCliente(com.example.observability.domain.entities.StatusCliente.Inativo);

        mockMvc.perform(get("/api/cliente/disable/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content().string(
                                new ObjectMapper().writeValueAsString(clienteRepresentationMock)
                        )
                );
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void disableCliente_BusinessException() throws Exception {
        clienteEntityMock.setStatusCliente(StatusCliente.Inativo);

        mockMvc.perform(get("/api/cliente/disable/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void saveCliente_success() throws Exception {
        ClienteRepresentation clienteRepresentationMock = new EasyRandom().nextObject(ClienteRepresentation.class);
        clienteRepresentationMock.setStatusCliente(com.example.observability.domain.entities.StatusCliente.Ativo);

        ClienteEntity clienteEntityMock = new ClienteEntity(clienteRepresentationMock.toDomain());

        Mockito.when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(clienteEntityMock);

        mockMvc.perform(post("/api/cliente")
                        .content(objectMapper.writeValueAsString(clienteRepresentationMock))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content().string(
                                new ObjectMapper().writeValueAsString(clienteRepresentationMock)
                        )
                );
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void saveCliente_BusinessException() throws Exception {
        ClienteRepresentation clienteRepresentationMock = new EasyRandom().nextObject(ClienteRepresentation.class);
        clienteRepresentationMock.setStatusCliente(com.example.observability.domain.entities.StatusCliente.Inativo);

        mockMvc.perform(post("/api/cliente")
                        .content(objectMapper.writeValueAsString(clienteRepresentationMock))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isConflict());
    }

}
