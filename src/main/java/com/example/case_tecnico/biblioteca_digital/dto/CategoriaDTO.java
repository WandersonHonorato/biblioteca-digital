package com.example.case_tecnico.biblioteca_digital.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaDTO(
        Long id,

        @NotBlank(message = "Nome da categoria é obrigatório")
        String nome,

        String descricao
) {}
