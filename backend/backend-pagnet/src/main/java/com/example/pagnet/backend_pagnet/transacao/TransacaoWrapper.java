package com.example.pagnet.backend_pagnet.transacao;

import java.math.BigDecimal;
import java.util.List;

public record TransacaoWrapper(BigDecimal total, String nomeLoja, List<Transacao> transacoes) {

    public TransacaoWrapper addTotal(BigDecimal valor) {
        return new TransacaoWrapper(this.total.add(valor), this.nomeLoja, this.transacoes);
    }

    public TransacaoWrapper addTransacao(Transacao transacao) {
        var transacoes = this.transacoes();
        transacoes.add(transacao);
        return new TransacaoWrapper(this.total, this.nomeLoja, transacoes);
    }

    public TransacaoWrapper withNomeDaLoja(String nomeDaLoja) {
        return new TransacaoWrapper(this.total, nomeDaLoja, this.transacoes);
    }
}
