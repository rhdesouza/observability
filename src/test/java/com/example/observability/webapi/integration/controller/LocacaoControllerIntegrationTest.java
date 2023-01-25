package com.example.observability.webapi.integration.controller;


import com.example.observability.domain.entities.Locacao;
import com.example.observability.infrastructure.persistence.entity.ClienteEntity;
import com.example.observability.infrastructure.persistence.entity.LocacaoEntity;
import com.example.observability.infrastructure.persistence.entity.StatusVeiculo;
import com.example.observability.infrastructure.persistence.entity.VeiculoEntity;
import com.example.observability.infrastructure.persistence.repository.ClienteRepository;
import com.example.observability.infrastructure.persistence.repository.LocacaoRepository;
import com.example.observability.infrastructure.persistence.repository.VeiculoRepository;
import com.example.observability.webapi.representation.LocacaoRepresentationSimple;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LocacaoControllerIntegrationTest {
    private final String URL_CONTROLLER = "/api/locacao";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocacaoRepository locacaoRepository;

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private VeiculoRepository veiculoRepository;

    private LocacaoRepresentationSimple locacaoRepresentationSimpleMock;

    private List<LocacaoRepresentationSimple> locacaoRepresentationSimplesListMock;

    @BeforeEach
    public void init() {
        LocacaoEntity locacaoEntityMock = new EasyRandom().nextObject(LocacaoEntity.class);
        Mockito.when(locacaoRepository.findById(1L)).thenReturn(Optional.of(locacaoEntityMock));
        locacaoRepresentationSimpleMock = new LocacaoRepresentationSimple(locacaoEntityMock.toDomain());


        List<LocacaoEntity> locacaoEntityListMock = new EasyRandom().objects(LocacaoEntity.class, 15).collect(Collectors.toList());
        Mockito.when(locacaoRepository.findAll()).thenReturn(locacaoEntityListMock);
        List<Locacao> clientesMock = locacaoEntityListMock.stream().map(locacao -> locacao.toDomain()).toList();
        locacaoRepresentationSimplesListMock = clientesMock.stream().map(LocacaoRepresentationSimple::new).toList();
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void getLocacao_success() throws Exception {
        mockMvc.perform(get(URL_CONTROLLER + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").value(locacaoRepresentationSimpleMock.getId())
                );
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void getLocacao_notFoundException() throws Exception {
        mockMvc.perform(get(URL_CONTROLLER + "/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void getAllLocacoes_success() throws Exception {

        mockMvc.perform(get(URL_CONTROLLER)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$", hasSize(15))
                );
    }


    @Test
    @WithMockUser(username = "test", password = "test")
    void setLocacao_success() throws Exception {

        ClienteEntity clienteEntityMock = new EasyRandom().nextObject(ClienteEntity.class);
        Mockito.when(clienteRepository.findById(1l)).thenReturn(Optional.of(clienteEntityMock));

        VeiculoEntity veiculoEntityMock = new EasyRandom().nextObject(VeiculoEntity.class);
        veiculoEntityMock.setStatus(StatusVeiculo.Disponivel);
        Mockito.when(veiculoRepository.findById(2l)).thenReturn(Optional.of(veiculoEntityMock));

        Locacao locacaoMock = new Locacao(veiculoEntityMock.toDomain(), clienteEntityMock.toDomain());
        locacaoMock.setId(2000);
        LocacaoEntity locacaoEntityMock = new LocacaoEntity(locacaoMock);

        Mockito.when(locacaoRepository.save(any(LocacaoEntity.class))).thenReturn(locacaoEntityMock);
        Mockito.when(veiculoRepository.save(any(VeiculoEntity.class))).thenReturn(veiculoEntityMock);

        mockMvc.perform(post(URL_CONTROLLER + "/rent/1/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").value(locacaoMock.getId())
                );
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void setDevolucao_success() throws Exception {

        LocacaoEntity locacaoEntityMock = new EasyRandom().nextObject(LocacaoEntity.class);
        Mockito.when(locacaoRepository.findById(any(Long.class))).thenReturn(Optional.of(locacaoEntityMock));
        Mockito.when(locacaoRepository.save(any(LocacaoEntity.class))).thenReturn(locacaoEntityMock);

        VeiculoEntity veiculoEntityMock = new EasyRandom().nextObject(VeiculoEntity.class);
        veiculoEntityMock.setStatus(StatusVeiculo.Disponivel);
        Mockito.when(veiculoRepository.save(any(VeiculoEntity.class))).thenReturn(veiculoEntityMock);



        mockMvc.perform(post(URL_CONTROLLER + "/devolution/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").value(locacaoEntityMock.getId())
                );

    }

    @Test
    void LocacaoController_unauthorized() throws Exception {
        mockMvc.perform(get(URL_CONTROLLER + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get(URL_CONTROLLER)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post(URL_CONTROLLER + "/rent/1/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }

}
