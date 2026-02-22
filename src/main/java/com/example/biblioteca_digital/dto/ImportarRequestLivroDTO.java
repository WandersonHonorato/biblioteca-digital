package com.example.biblioteca_digital.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ImportarRequestLivroDTO(
        @NotBlank(message = "URL é obrigatória")
        @Pattern(regexp = "^https?://.*", message = "URL deve começar com http:// ou https://")
        String url,
        @NotNull(message = "ID do autor é obrigatório")
        Long autorId,
        @NotNull(message = "ID da categoria é obrigatório")
        Long categoriaId
) {}

