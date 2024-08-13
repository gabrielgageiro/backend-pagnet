package com.example.pagnet.backend_pagnet.transacao;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public List<TransacaoWrapper> getTotaisByLoja() {
        List<Transacao> transacaoList = transacaoRepository.findAll();
        Map<String, TransacaoWrapper> map = new LinkedHashMap<>();

        transacaoList.forEach(transacao -> {
            String nomeDaLoja = transacao.nomeLoja();
            BigDecimal valor = transacao.valor();

            map.compute(nomeDaLoja, (key, value) -> {
                TransacaoWrapper wrapper = (value != null) ? value
                        : new TransacaoWrapper(BigDecimal.ZERO, key, new ArrayList<>());

                return wrapper.addTotal(valor).addTransacao(transacao);
            });
        });

        return new ArrayList<>(map.values());
    }
}
