package com.example.observability.domain.interfaces;

import com.example.observability.domain.entities.Locacao;

import java.util.List;
import java.util.Optional;

public interface LocacaoService {

    Locacao findByIdLocacao(Long idLocacao);

    List<Locacao> getAllLocacao();

    Locacao setLocacao(Long idCliente, Long idVeiculo);

    Locacao setDevolucao(Long idLocacao);
}
