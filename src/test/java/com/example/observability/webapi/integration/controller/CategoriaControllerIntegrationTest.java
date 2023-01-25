package com.example.observability.webapi.integration.controller;

import com.example.observability.infrastructure.persistence.entity.CategoriaEntity;
import com.example.observability.infrastructure.persistence.repository.CategoriaRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoriaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CategoriaRepository categoriaRepository;

    CategoriaEntity categoriaEntityMock;

    List<CategoriaEntity> categoriaEntityListMock;

    @BeforeEach
    public void init() {
        categoriaEntityMock = new EasyRandom().nextObject(CategoriaEntity.class);
        Mockito.when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaEntityMock));

        categoriaEntityListMock = new EasyRandom().objects(CategoriaEntity.class, 15).collect(Collectors.toList());
        Mockito.when(categoriaRepository.findAll()).thenReturn(categoriaEntityListMock);
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void getCategoria_success() throws Exception {
        mockMvc.perform(get("/api/categoria/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoria").value(categoriaEntityMock.getCategoria()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valorDiariaMinima").value(categoriaEntityMock.getValorDiariaMinima()));
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void getCategoria_notFound() throws Exception {
        mockMvc.perform(get("/api/categoria/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void getAllCategorias_success() throws Exception {
        mockMvc.perform(get("/api/categoria")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(15)));
    }

}
