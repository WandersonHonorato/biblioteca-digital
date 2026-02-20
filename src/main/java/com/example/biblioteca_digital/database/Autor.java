package com.example.biblioteca_digital.database;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido")
    @Column(name = "email", length = 60, nullable = false, unique = true)
    private String email;
    @NotNull(message = "Data de nascimento é obrigatório")
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Livro> livros = new ArrayList<>();
}
