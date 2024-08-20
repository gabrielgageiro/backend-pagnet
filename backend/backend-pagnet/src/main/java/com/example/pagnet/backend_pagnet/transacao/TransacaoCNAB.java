package com.example.pagnet.backend_pagnet.transacao;

import java.math.BigDecimal;

public record TransacaoCNAB(
        Integer tipo,
        String data,
        BigDecimal valor,
        String cpf,
        String cartao,
        String hora,
        String donoLoja,
        String nomeLoja
) {

}
