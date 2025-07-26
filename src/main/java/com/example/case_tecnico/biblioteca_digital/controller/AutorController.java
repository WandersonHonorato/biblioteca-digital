package com.example.case_tecnico.biblioteca_digital.controller;

import com.example.case_tecnico.biblioteca_digital.dto.AutorDTO;
import com.example.case_tecnico.biblioteca_digital.dto.LivroDTO;
import com.example.case_tecnico.biblioteca_digital.service.AutorService;
import com.example.case_tecnico.biblioteca_digital.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/api/autores")
@RestController
public class AutorController {

    private final AutorService autorService;
    private final LivroService livroService;

    @GetMapping
    public ResponseEntity<List<AutorDTO>> listarTodos() {
        return ResponseEntity.ok(autorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(autorService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AutorDTO> criar(@RequestBody @Valid AutorDTO dto) {
        AutorDTO novo = autorService.criar(dto);
        URI uri = URI.create("/api/autores/" + novo.id());
        return ResponseEntity.created(uri).body(novo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AutorDTO dto) {
        return ResponseEntity.ok(autorService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        autorService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/livros")
    public ResponseEntity<List<LivroDTO>> listarLivrosPorAutor(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.listarPorAutor(id));
    }
}