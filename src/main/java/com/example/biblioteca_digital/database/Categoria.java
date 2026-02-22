package com.example.biblioteca_digital.database;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "nome", length = 100, nullable = false, unique = true)
    private String nome;

    @Column(name = "desccrição", length = 250)
    private String descricao;

    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    private List<Livro> livros = new ArrayList<>();

}

