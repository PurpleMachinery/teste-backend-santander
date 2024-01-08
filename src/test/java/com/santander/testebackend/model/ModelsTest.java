package com.santander.testebackend.model;


import com.santander.testebackend.model.enums.TipoTransacao;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(MockitoJUnitRunner.class)
public class ModelsTest {
    @Test
    public void deveTestarTaxa() {
        Taxa taxa = new Taxa();
        taxa.setId(1);
        taxa.setPorcentagem(0);
        taxa.setValorInicial(new BigDecimal("0"));

        Assertions.assertEquals(1, taxa.getId());
        Assertions.assertEquals(0, taxa.getPorcentagem());
        Assertions.assertNotNull(taxa.getValorInicial());
    }

    @Test
    public void deveTestarHistoricoTransacao() {
        HistoricoTransacao taxa = new HistoricoTransacao();
        taxa.setId(1);
        taxa.setNumeroConta("111111");
        taxa.setDataMovimentacao(LocalDate.now());
        taxa.setTipoTransacao(TipoTransacao.SAQUE);
        taxa.setPlanoExclusive(false);
        taxa.setValor(new BigDecimal("0"));

        Assertions.assertEquals(1, taxa.getId());
        Assertions.assertEquals("111111", taxa.getNumeroConta());
        Assertions.assertEquals(LocalDate.now(), taxa.getDataMovimentacao());
        Assertions.assertEquals(TipoTransacao.SAQUE, taxa.getTipoTransacao());
        Assertions.assertFalse(taxa.isPlanoExclusive());
        Assertions.assertNotNull(taxa.getValor());
    }
}
