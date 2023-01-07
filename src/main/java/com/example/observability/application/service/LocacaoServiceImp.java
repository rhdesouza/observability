package com.example.observability.application.service;

import com.example.observability.domain.entities.Cliente;
import com.example.observability.domain.entities.Locacao;
import com.example.observability.domain.entities.StatusVeiculo;
import com.example.observability.domain.entities.Veiculo;
import com.example.observability.domain.interfaces.ClienteService;
import com.example.observability.domain.interfaces.LocacaoService;
import com.example.observability.domain.interfaces.VeiculoService;
import com.example.observability.infrastructure.persistence.entity.LocacaoEntity;
import com.example.observability.infrastructure.persistence.repository.ClienteRepository;
import com.example.observability.infrastructure.persistence.repository.LocacaoRepository;
import com.example.observability.webapi.exception.BusinessException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocacaoServiceImp implements LocacaoService {

    Logger logger = LoggerFactory.getLogger(LocacaoServiceImp.class);

    @Autowired
    LocacaoRepository locacaoRepository;

    @Autowired
    ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VeiculoService veiculoService;

    @Override
    public Optional<Locacao> findByIdLocacao(Long idLocacao) {
        Optional<LocacaoEntity> locacaoEntity = locacaoRepository.findById(idLocacao);
        if (locacaoEntity.isPresent()) {
            logger.info(String.format("Method: findByIdLocacao | retunr %s", locacaoEntity.get()));
            return Optional.of(locacaoEntity.get().toDomain());
        } else {
            logger.info("Method: findByIdLocacao | retunr not found");
            return null;
        }
    }

    @Override
    public List<Locacao> getAllLocacao() {
        List<LocacaoEntity> locacaoEntities = locacaoRepository.findAll();

        List<Locacao> locacoes = locacaoEntities.stream().map(
                locacao -> locacao.toDomain()
        ).toList();

        logger.info(String.format("Method: getAllLocacao | find to %s clientes", locacoes.size()));
        return locacoes;
    }

    @Override
    public Locacao setLocacao(Long idCliente, Long idVeiculo) {
        Cliente cliente = clienteService.findByIdCliente(idCliente).get();
        Veiculo veiculo = veiculoService.findByIdVeiculo(idVeiculo).get();
        isVeiculoAlugado(veiculo);

        Locacao locacao = new Locacao(veiculo, cliente);
        try {
            LocacaoEntity locacaoEntity = locacaoRepository.save(new LocacaoEntity(locacao));
            veiculoService.setStatusVeiculo(veiculo, StatusVeiculo.Alugado);
            logger.info(String.format("Method: getAllLocacao | Saved locacao %s", locacaoEntity));
            return locacaoEntity.toDomain();
        } catch (Exception ex) {
            logger.error(String.format("Method: getAllLocacao | Error to save locacao"));
            throw ex;
        }
    }

    private void isVeiculoAlugado(@NotNull Veiculo veiculo){
        if (veiculo.getStatus() == StatusVeiculo.Alugado){
            logger.info(String.format("Method: isVeiculoAlugado | vehicle %s is already in place", veiculo));
            throw new BusinessException("Veículo ja foi aluago, espera até a devolução");
        }
    }

    @Override
    public Locacao setDevolucao(Long idLocacao) {
        Locacao locacao = findByIdLocacao(idLocacao).get();
        try {
            locacao.setDevolucao();
            veiculoService.setStatusVeiculo(locacao.getVeiculo(), StatusVeiculo.Disponivel);
            return locacaoRepository.save(new LocacaoEntity(locacao)).toDomain();
        } catch (Exception ex) {
            logger.error(String.format("Method: setDevolucao | Error to save locacao devolucao"));
            throw ex;
        } finally {
            logger.info(String.format("Method: setDevolucao | Saved locacao %s", locacao));
        }
    }
}
