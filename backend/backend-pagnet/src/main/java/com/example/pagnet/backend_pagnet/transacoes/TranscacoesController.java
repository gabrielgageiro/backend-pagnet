package com.example.pagnet.backend_pagnet.transacoes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transacoes")
public class TranscacoesController {

    public TranscacoesController() {

    }

    @GetMapping
    public String transacao() {
        return "Transação";
    }
}
