package com.example.biblioteca_digital.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaDTO(
        Long id,

        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
        String nome,
        @Size(max = 350, message = "Descrição deve ter no máximo 350 caracteres")
        String descricao
        private Integer totalLivros;
) {}
