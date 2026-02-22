package com.example.biblioteca_digital.dto;

public record ImportarResponseLivroDTO(
        LivroRequestDTO livroImportado,
        String status,
        String mensagem
) {}
