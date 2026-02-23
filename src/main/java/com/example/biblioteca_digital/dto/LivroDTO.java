package com.example.biblioteca_digital.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LivroDTO(

        @NotBlank(message = "O título é obrigatório")
        @Size(max = 100, message = "Título deve ter no máximo 100 caracteres")
        String titulo,
        @NotBlank(message = "ISBN é obrigatório")
        @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISBN deve ter 10 ou 13 dígitos")
        String isbn,

        @NotNull(message = "Ano de publicação é obrigatório")
        @Min(value = 1000, message = "Ano de publicação deve ser válido")
        @Max(value = 2025, message = "Ano de publicação não pode ser futuro")
        Integer anoPublicacao,

        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "O preço tem que ser um número positivo")
        @DecimalMin(value = "0.1", inclusive = false)
        BigDecimal preco,

        @NotNull(message = "ID do autor é obrigatório")
        Long autorId,

        @NotNull(message = "ID da categoria é obrigatório")
        Long categoriaId

        @Size(max = 500, message = "URL de origem deve ter no máximo 500 caracteres")
        String urlOrigem,

        LocalDateTime dataCadastro,
        LocalDateTime dataAtualizacao,
        AutorDTO autor,
        CategoriaDTO categoria,
) {}

