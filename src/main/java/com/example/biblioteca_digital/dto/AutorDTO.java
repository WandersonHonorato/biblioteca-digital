package com.example.biblioteca_digital.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record AutorDTO(
        Long id,
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,
        @NotNull(message = "Data de nascimento é obrigatória")
        @Past(message = "Data de nascimento deve estar no passado")
        LocalDate dataNascimento

        Integer totalLivros
) {}