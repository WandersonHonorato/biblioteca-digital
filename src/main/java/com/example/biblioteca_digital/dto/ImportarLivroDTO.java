package com.example.biblioteca_digital.dto;

public record ImportarLivroDTO (
        String url,
        Long autorId,
        Long categoriaId
) {}

