package com.example.pagnet.backend_pagnet.transacao;

import com.example.pagnet.backend_pagnet.arquivos.ArquivosService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/transacoes")
public class TranscacoesController {

    private ArquivosService arquivosService;

    public TranscacoesController(ArquivosService arquivosService) {
        this.arquivosService = arquivosService;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws Exception {
        arquivosService.upload(file);
        return "Processando";
    }
}
