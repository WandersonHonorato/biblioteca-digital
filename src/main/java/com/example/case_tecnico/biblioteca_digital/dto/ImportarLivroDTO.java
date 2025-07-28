package com.example.case_tecnico.biblioteca_digital.dto;

public record ImportarLivroDTO (
        String url,
        Long autorId,
        Long categoriaId
) {}

