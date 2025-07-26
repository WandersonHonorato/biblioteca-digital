package com.example.case_tecnico.biblioteca_digital.repository;

import com.example.case_tecnico.biblioteca_digital.model.Categoria;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
