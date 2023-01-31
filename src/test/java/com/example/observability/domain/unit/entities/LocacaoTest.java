package com.example.observability.domain.unit.entities;

import com.example.observability.domain.entities.Cliente;
import com.example.observability.domain.entities.Locacao;
import com.example.observability.domain.entities.Veiculo;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.jeasy.random.FieldPredicates.named;

@SpringBootTest
class LocacaoTest {

    @Test
    void constructLocacaoDomain() {
        Locacao locacaoRandom = new EasyRandom().nextObject(Locacao.class);

        Locacao locacao = new Locacao(
                locacaoRandom.getId(),
                locacaoRandom.getVeiculo(),
                locacaoRandom.getCliente(),
                locacaoRandom.getDataLocacao(),
                locacaoRandom.getDataDevolucao(),
                locacaoRandom.getValor()
        );

        Assertions.assertEquals(locacao, locacaoRandom);
        Assertions.assertEquals(locacao.toString(), locacaoRandom.toString());
        Assertions.assertEquals(locacao.hashCode(), locacaoRandom.hashCode());
        Assertions.assertTrue(locacao.equals(locacaoRandom));
    }


    @Test
    void constructLocacaoDomain_Devolucao_AbaixoDoMinimo() {
        BigDecimal valorDiariaMinima = new BigDecimal(200);

        Veiculo veiculoMock = new EasyRandom(
                new EasyRandomParameters()
                        .randomize(named("anoModelo"), () -> 2022)
                        .randomize(named("valorFipe"), () -> new BigDecimal(50000))
                        .randomize(named("valorDiariaMinima"), () -> valorDiariaMinima)
        ).nextObject(Veiculo.class);

        Cliente clienteMock = new EasyRandom().nextObject(Cliente.class);

        Locacao locacao = new Locacao(veiculoMock, clienteMock);
        locacao.setDevolucao();

        Assertions.assertEquals(locacao.getVeiculo(), veiculoMock);
        Assertions.assertEquals(locacao.getCliente(), clienteMock);
        Assertions.assertEquals(locacao.getValor(), valorDiariaMinima);
    }

    @Test
    void constructLocacaoDomain_Devolucao_AcimaDoMinimo() {
        BigDecimal valorDiariaMinima = new BigDecimal(100);

        Veiculo veiculoMock = new EasyRandom(
                new EasyRandomParameters()
                        .randomize(named("anoModelo"), () -> 2022)
                        .randomize(named("valorFipe"), () -> new BigDecimal(50000))
                        .randomize(named("valorDiariaMinima"), () -> valorDiariaMinima)
        ).nextObject(Veiculo.class);

        Cliente clienteMock = new EasyRandom().nextObject(Cliente.class);

        Locacao locacao = new Locacao(veiculoMock, clienteMock);
        locacao.setDevolucao();

        Assertions.assertEquals(locacao.getVeiculo(), veiculoMock);
        Assertions.assertEquals(locacao.getCliente(), clienteMock);
        Assertions.assertTrue(locacao.getValor().compareTo(valorDiariaMinima) == 1);
    }

}
