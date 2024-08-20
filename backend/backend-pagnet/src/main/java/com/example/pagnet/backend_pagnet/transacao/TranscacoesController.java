package com.example.pagnet.backend_pagnet.transacao;

import com.example.pagnet.backend_pagnet.arquivos.ArquivosService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
public class TranscacoesController {

    private final ArquivosService arquivosService;
    private final TransacaoService transacaoService;

    public TranscacoesController(ArquivosService arquivosService, TransacaoService transacaoService) {
        this.arquivosService = arquivosService;
        this.transacaoService = transacaoService;
    }

    @PostMapping("/upload")
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
    public String upload(@RequestParam("file") MultipartFile file) throws Exception {
        arquivosService.upload(file);
        return "Processando";
    }

    @GetMapping()
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
    public List<TransacaoWrapper> list() {
        return transacaoService.getTotaisByLoja();
    }
}
