package com.example.observability.webapi.integration.controller;

import com.example.observability.domain.entities.Veiculo;
import com.example.observability.infrastructure.persistence.entity.VeiculoEntity;
import com.example.observability.infrastructure.persistence.repository.ClienteRepository;
import com.example.observability.infrastructure.persistence.repository.VeiculoRepository;
import com.example.observability.webapi.representation.VeiculoRepresentation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.hamcrest.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VeiculoControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    public VeiculoRepository veiculoRepository;

    private VeiculoRepresentation veiculoRepresentationMock;

    private List<VeiculoRepresentation> veiculoRepresentationListMock;
    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void init() {
        VeiculoEntity veiculoEntityMock = new EasyRandom().nextObject(VeiculoEntity.class);
        Mockito.when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculoEntityMock));
        veiculoRepresentationMock = new VeiculoRepresentation(veiculoEntityMock.toDomain());

        List<VeiculoEntity> veiculoEntityListMock = new EasyRandom().objects(VeiculoEntity.class, 15).collect(Collectors.toList());
        Mockito.when(veiculoRepository.findAll()).thenReturn(veiculoEntityListMock);
        List<Veiculo> veiculosMock = veiculoEntityListMock.stream().map(veiculo -> veiculo.toDomain()).toList();
        veiculoRepresentationListMock = veiculosMock.stream().map(VeiculoRepresentation::new).toList();
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void getCategoria_success() throws Exception {
        mockMvc.perform(get("/api/veiculo/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content().string(
                                new ObjectMapper().writeValueAsString(veiculoRepresentationMock)
                        )
                );
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void getCategoria_notFound() throws Exception {
        mockMvc.perform(get("/api/veiculo/55")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void getAllVeiculo_success() throws Exception {
        mockMvc.perform(get("/api/veiculo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content().string(
                                new ObjectMapper().writeValueAsString(veiculoRepresentationListMock)
                        )
                );
    }
}
