package com.example.case_tecnico.biblioteca_digital.repository;

import com.example.case_tecnico.biblioteca_digital.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.ScopedValue;
import java.util.Arrays;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

}
