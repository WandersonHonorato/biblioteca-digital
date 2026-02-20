package com.example.biblioteca_digital.database;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "Autor")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;

    @Pattern(regexp = "^(\\d{10}|\\d{13})$")
    private String isbn;

    @Positive
    private BigDecimal preco;
    private Integer anoPublicacao;

    @ManyToOne
    private Autor autor;

    @ManyToOne
    private Categoria categoria;
}
