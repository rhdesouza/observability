package com.example.observability.webapi.integration.controller;

import com.example.observability.domain.entities.Montadora;
import com.example.observability.infrastructure.persistence.entity.MontadoraEntity;
import com.example.observability.infrastructure.persistence.repository.MontadoraRepository;
import com.example.observability.webapi.representation.MontadoraRepresentation;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MontadoraControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    public MontadoraRepository montadoraRepository;

    private MontadoraRepresentation montadoraRepresentationMock;

    private List<MontadoraRepresentation> montadoraRepresentationListMock;

    @BeforeEach
    public void init() {
        MontadoraEntity montadoraEntityMock = new EasyRandom().nextObject(MontadoraEntity.class);
        Mockito.when(montadoraRepository.findById(1L)).thenReturn(Optional.of(montadoraEntityMock));
        montadoraRepresentationMock = new MontadoraRepresentation(montadoraEntityMock.toDomain());

        List<MontadoraEntity> montadoraEntityListMock = new EasyRandom().objects(MontadoraEntity.class, 15).collect(Collectors.toList());
        Mockito.when(montadoraRepository.findAll()).thenReturn(montadoraEntityListMock);
        List<Montadora> montadorasMock = montadoraEntityListMock.stream().map(montadora -> montadora.toDomain()).toList();
        montadoraRepresentationListMock = montadorasMock.stream().map(MontadoraRepresentation::new).toList();
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void getMontadora_success() throws Exception {
        mockMvc.perform(get("/api/montadora/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content().string(
                                new ObjectMapper().writeValueAsString(montadoraRepresentationMock)
                        )
                );
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void getMontadora_notFound() throws Exception {
        mockMvc.perform(get("/api/montadora/55")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getMontadora_unauthorized() throws Exception {
        mockMvc.perform(get("/api/montadora/55")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void getAllMontadora_success() throws Exception {
        mockMvc.perform(get("/api/montadora")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content().string(
                                new ObjectMapper().writeValueAsString(montadoraRepresentationListMock)
                        )
                );
    }
}
