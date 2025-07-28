package com.example.case_tecnico.biblioteca_digital.repository;

import com.example.case_tecnico.biblioteca_digital.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository             
public interface LivroRepository extends JpaRepository<Livro, Long> {
        boolean existsByIsbn(String isbn);
        List<Livro> findByTituloContainingIgnoreCase(String titulo);
}
