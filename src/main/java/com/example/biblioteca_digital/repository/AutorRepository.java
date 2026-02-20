package com.example.biblioteca_digital.repository;

import com.example.biblioteca_digital.database.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.ScopedValue;
import java.util.Arrays;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

}
