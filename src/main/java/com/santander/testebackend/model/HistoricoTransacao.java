package com.santander.testebackend.model;

import com.santander.testebackend.model.enums.TipoTransacao;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class HistoricoTransacao extends Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate dataMovimentacao;
    private TipoTransacao tipoTransacao;
    private boolean planoExclusive;


    public static class HistoricoTransacaoBuilder {
        private HistoricoTransacao historicoTransacao;

        public HistoricoTransacaoBuilder(Cliente cliente, Transacao transacao, TipoTransacao tipoTransacao) {
            historicoTransacao = new HistoricoTransacao();

            cliente(cliente);
            transacao(transacao);
            tipoTransacao(tipoTransacao);
        }

        private HistoricoTransacaoBuilder cliente(Cliente cliente) {
            historicoTransacao.setNumeroConta(cliente.getNumeroConta());
            historicoTransacao.setPlanoExclusive(cliente.isPlanoExclusive());

            return this;
        }

        private HistoricoTransacaoBuilder transacao(Transacao transacao) {
            historicoTransacao.setValor(transacao.getValor());

            return this;
        }

        private HistoricoTransacao tipoTransacao(TipoTransacao tipoTransacao) {
            historicoTransacao.setTipoTransacao(tipoTransacao);

            return build();
        }

        public HistoricoTransacao build(){
            historicoTransacao.setDataMovimentacao(LocalDate.now());

            return historicoTransacao;
        }
    }

}
