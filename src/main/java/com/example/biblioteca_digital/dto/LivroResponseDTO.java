package com.example.biblioteca_digital.dto;

import java.time.LocalDateTime;

public record LivroResponseDTO(
        Long id,
        LocalDateTime dataCadastro,
        LocalDateTime dataAtualizacao,

        AutorDTO autor,
        CategoriaDTO categoria
) {
}
