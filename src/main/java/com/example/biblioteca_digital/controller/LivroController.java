package com.example.biblioteca_digital.controller;

import com.example.biblioteca_digital.dto.ImportarRequestLivroDTO;
import com.example.biblioteca_digital.dto.LivroRequestDTO;
import com.example.biblioteca_digital.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/livros")
@RestController
public class LivroController {

    private final LivroService livroService;

    @GetMapping
    public ResponseEntity<List<LivroRequestDTO>> listarTodos() {
        return ResponseEntity.ok(livroService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroRequestDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<LivroRequestDTO> criar(@RequestBody @Valid LivroRequestDTO dto) {
        LivroRequestDTO novo = livroService.criar(dto);
        URI uri = URI.create("/api/livros/" + novo.id());
        return ResponseEntity.created(uri).body(novo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroRequestDTO> atualizar(@PathVariable Long id, @RequestBody @Valid LivroRequestDTO dto) {
        return ResponseEntity.ok(livroService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<LivroRequestDTO>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(livroService.buscarPorTitulo(titulo));
    }

    @PostMapping("/importar")
    public ResponseEntity<LivroRequestDTO> importar(@RequestBody @Valid ImportarRequestLivroDTO request) {
        return ResponseEntity.ok(livroService.importarLivroViaScraping(request));
    }
}
