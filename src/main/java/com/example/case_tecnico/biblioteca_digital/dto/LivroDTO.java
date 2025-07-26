package com.example.case_tecnico.biblioteca_digital.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;

public record LivroDTO(
        Long id,

        @NotBlank(message = "O título é obrigatório")
        String titulo,

        @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISBN deve ter 10 ou 13 dígitos")
        String isbn,

        @NotNull(message = "O ano de publicação é obrigatório")
        @Max(value = 2100, message = "O ano de publicação é inválido")
        Integer anoPublicacao,

        @NotNull(message = "O preço é obrigatório")
        @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser um número positivo")
        BigDecimal preco,

        @NotNull(message = "ID do autor é obrigatório")
        Long autorId,

        @NotNull(message = "ID da categoria é obrigatório")
        Long categoriaId
) {}

