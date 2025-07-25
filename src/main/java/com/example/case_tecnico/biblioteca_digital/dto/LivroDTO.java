package com.example.case_tecnico.biblioteca_digital.dto;

import java.math.BigDecimal;

public record LivroDTO(
        String titulo,
        String isbn,
        BigDecimal preco,
        Integer anoPublicacao
) {}
