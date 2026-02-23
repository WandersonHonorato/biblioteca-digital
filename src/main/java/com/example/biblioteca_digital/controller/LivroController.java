package com.example.biblioteca_digital.controller;

import com.example.biblioteca_digital.dto.ImportarRequestLivroDTO;
import com.example.biblioteca_digital.dto.LivroRequestDTO;
import com.example.biblioteca_digital.dto.LivroResponseDTO;
import com.example.biblioteca_digital.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/api/livros")
@RestController
public class LivroController {

    private final LivroService livroService;

    @GetMapping
    public ResponseEntity<Page<LivroRequestDTO>> listarTodos(
          @RequestParam(required = false) Long categoriaId) {
        return ResponseEntity.ok(buscarComFiltros.listarTodos(categoriaId, anoPublicacao, autorId));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LivroResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<LivroResponseDTO> criar(@RequestBody @Valid LivroRequestDTO livroRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.criar(livroRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid LivroRequestDTO livroRequestDTO) {
        return ResponseEntity.ok(livroService.atualizar(id,livroRequestDTO));
    }

    @DeleteMapping(value ="/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<LivroRequestDTO>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(livroService.buscarPorTitulo(titulo));
    }

    @GetMapping("/buscar/paginado")
    public ResponseEntity<LivroResponseDTO> buscarPorTituloPaginado(
            @RequestParam String titulo,
            @PageableDefault(size = 10, sort = "titulo") Pageable pageable) {
        Optional<LivroResponseDTO> tituloPaginado = livroService.buscarPorTitulo(titulo);
        if (tituloPaginado.isPresent()) {
            return ResponseEntity.ok(tituloPaginado.get());
        }
        return ResponseEntity.ok(livroService.buscarPorTitulo(titulo, pageable));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<LivroResponseDTO> buscarPorIsbn(@PathVariable String isbn) {
        Optional<LivroResponseDTO> livro = livroService.buscarPorIsbn(isbn);
        if (livro.isPresent()) {
            return ResponseEntity.ok(livro.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/verificar-isbn")
    public ResponseEntity<Boolean> verificarIsbnExiste(@RequestParam String isbn) {
        return ResponseEntity.ok(livroService.existePorIsbn(isbn));
    }
}
    @PostMapping("/importar")
    public ResponseEntity<LivroRequestDTO> importar(@RequestBody @Valid ImportarRequestLivroDTO request) {
        return ResponseEntity.ok(livroService.importarLivroViaScraping(request));
    }
}
