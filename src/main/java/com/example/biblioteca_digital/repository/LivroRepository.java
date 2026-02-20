package com.example.biblioteca_digital.repository;

import com.example.biblioteca_digital.database.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository             
public interface LivroRepository extends JpaRepository<Livro, Long> {
        boolean existsByIsbn(String isbn);
        List<Livro> findByTituloContainingIgnoreCase(String titulo);
}
