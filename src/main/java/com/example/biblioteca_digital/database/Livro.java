package com.example.biblioteca_digital.database;

import com.example.biblioteca_digital.database.Autor;
import com.example.biblioteca_digital.database.Categoria;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Column(name= "titulo", length = 100, nullable = false)
    private String titulo;

    @NotBlank(message = "ISBN é obrigatório")
    @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISBN deve conter entre 10 a 13 números")
    @Column(name = "isbn", length = 13, nullable = false, unique = true)
    private String isbn;

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser um número positivo")
    @Column(name = "preco", precision = 10, scale = 2, nullable = false)
    private BigDecimal preco;

    @NotNull(message = "Ano de publicação é obrigatório")
    @Column(name = "ano_publicacao", nullable = false)
    private Integer anoPublicacao;

    @NotNull(message = "Autor é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @NotNull(message = "Categoria é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Column(name = "url-origem", length = 350)
    private String urlOrigem;

    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

   @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
}
