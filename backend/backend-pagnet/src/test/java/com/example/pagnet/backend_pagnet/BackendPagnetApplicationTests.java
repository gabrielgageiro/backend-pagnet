package com.example.pagnet.backend_pagnet;

import com.example.pagnet.backend_pagnet.transacao.Transacao;
import com.example.pagnet.backend_pagnet.transacao.TransacaoRepository;
import com.example.pagnet.backend_pagnet.transacao.TransacaoService;
import com.example.pagnet.backend_pagnet.transacao.TransacaoWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class TransacaoServiceTest {

	@Mock
	private TransacaoRepository transacaoRepository;

	@InjectMocks
	private TransacaoService transacaoService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}


	@Test
	void getTotaisByLoja_ReturnsCorrectTotals_WhenTransactionsExist() {
		Transacao transacao1 = new Transacao(1, LocalDate.now(), new BigDecimal("100.00"), "12345678901", "1234", LocalTime.now(), "Dono1", "Loja1");
		Transacao transacao2 = new Transacao(1, LocalDate.now(), new BigDecimal("200.00"), "12345678901", "1234", LocalTime.now(), "Dono1", "Loja1");
		Transacao transacao3 = new Transacao(2, LocalDate.now(), new BigDecimal("300.00"), "12345678901", "1234", LocalTime.now(), "Dono2", "Loja2");

		when(transacaoRepository.findAll()).thenReturn(Arrays.asList(transacao1, transacao2, transacao3));

		List<TransacaoWrapper> result = transacaoService.getTotaisByLoja();

		assertEquals(2, result.size());
		assertEquals(new BigDecimal("300.00"), result.get(0).total());
		assertEquals(new BigDecimal("300.00"), result.get(1).total());
		result.forEach(wrapper ->{
			if(wrapper.nomeDaLoja().equals("Loja1")){
				assertEquals(2, wrapper.transacoes().size());
				assertTrue(wrapper.transacoes().contains(transacao1));
				assertTrue(wrapper.transacoes().contains(transacao2));
			}else{
				assertEquals(1, wrapper.transacoes().size());
				assertTrue(wrapper.transacoes().contains(transacao3));
			}
		});
	}

	@Test
	void getTotaisByLoja_ReturnsEmptyList_WhenNoTransactionsExist() {
		when(transacaoRepository.findAll()).thenReturn(Collections.emptyList());

		List<TransacaoWrapper> result = transacaoService.getTotaisByLoja();

		assertTrue(result.isEmpty());
	}

	@Test
	void getTotaisByLoja_ReturnsCorrectTotals_WhenSingleTransactionExists() {
		Transacao transacao = new Transacao(1, LocalDate.now(), new BigDecimal("150.00"), "12345678901", "1234", LocalTime.now(), "Dono1", "Loja1");

		when(transacaoRepository.findAll()).thenReturn(Collections.singletonList(transacao));

		List<TransacaoWrapper> result = transacaoService.getTotaisByLoja();

		assertEquals(1, result.size());
		assertEquals(new BigDecimal("150.00"), result.get(0).total());
	}
}