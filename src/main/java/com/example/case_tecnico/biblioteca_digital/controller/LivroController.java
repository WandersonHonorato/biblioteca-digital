package com.example.case_tecnico.biblioteca_digital.controller;

import com.example.case_tecnico.biblioteca_digital.dto.ImportarLivroDTO;
import com.example.case_tecnico.biblioteca_digital.dto.LivroDTO;
import com.example.case_tecnico.biblioteca_digital.service.LivroService;
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
    public ResponseEntity<List<LivroDTO>> listarTodos() {
        return ResponseEntity.ok(livroService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<LivroDTO> criar(@RequestBody @Valid LivroDTO dto) {
        LivroDTO novo = livroService.criar(dto);
        URI uri = URI.create("/api/livros/" + novo.id());
        return ResponseEntity.created(uri).body(novo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> atualizar(@PathVariable Long id, @RequestBody @Valid LivroDTO dto) {
        return ResponseEntity.ok(livroService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<LivroDTO>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(livroService.buscarPorTitulo(titulo));
    }

    @PostMapping("/importar")
    public ResponseEntity<LivroDTO> importar(@RequestBody @Valid ImportarLivroDTO request) {
        return ResponseEntity.ok(livroService.importarLivroViaScraping(request));
    }
}
