package com.example.observability.webapi.unit.representation;

import com.example.observability.domain.entities.Locacao;
import com.example.observability.webapi.representation.LocacaoRepresentationSimple;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LocacaoRepresentationSimpleTest {

    @Test
    void constructor(){
        Locacao locacaoDomain = new EasyRandom().nextObject(Locacao.class);

        LocacaoRepresentationSimple locacaoRepresentationSimple = new LocacaoRepresentationSimple(locacaoDomain);

        Assertions.assertEquals(locacaoDomain.getId(), locacaoRepresentationSimple.getId());
        Assertions.assertEquals(locacaoDomain.getVeiculo(), locacaoRepresentationSimple.getVeiculo());
        Assertions.assertEquals(locacaoDomain.getCliente(), locacaoRepresentationSimple.getCliente());
        Assertions.assertEquals(locacaoDomain.getDataLocacao(), locacaoRepresentationSimple.getDataLocacao());
        Assertions.assertEquals(locacaoDomain.getDataDevolucao(), locacaoRepresentationSimple.getDataDevolucao());
        Assertions.assertEquals(locacaoDomain.getValor(), locacaoRepresentationSimple.getValor());
    }
}
