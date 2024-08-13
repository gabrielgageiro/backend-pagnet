package com.example.pagnet.backend_pagnet.transacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record Transacao (
        Integer tipo,
        LocalDate data,
        BigDecimal valor,
        String cpf,
        String cartao,
        LocalTime hora,
        String donoDaLoja,
        String nomeDaLoja
){
    public Transacao withValor(BigDecimal valor) {
        return new Transacao(this.tipo, this.data, valor, this.cpf, this.cartao, this.hora, this.donoDaLoja, this.nomeDaLoja);
    }

    public Transacao withData(String data) {
        var date = LocalDate.parse(data, DateTimeFormatter.ofPattern("yyyyMMdd"));
        return new Transacao(this.tipo, date, this.valor, this.cpf, this.cartao, this.hora, this.donoDaLoja, this.nomeDaLoja);
    }

    public Transacao withHora(String data) {
        var hora = LocalTime.parse(data, DateTimeFormatter.ofPattern("HHmmss"));
        return new Transacao(this.tipo, this.data, this.valor, this.cpf, this.cartao, hora, this.donoDaLoja, this.nomeDaLoja);
    }
}
