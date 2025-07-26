package com.example.case_tecnico.biblioteca_digital.service;

import com.example.case_tecnico.biblioteca_digital.dto.LivroDTO;
import com.example.case_tecnico.biblioteca_digital.model.Autor;
import com.example.case_tecnico.biblioteca_digital.model.Categoria;
import com.example.case_tecnico.biblioteca_digital.model.Livro;
import com.example.case_tecnico.biblioteca_digital.repository.AutorRepository;
import com.example.case_tecnico.biblioteca_digital.repository.CategoriaRepository;
import com.example.case_tecnico.biblioteca_digital.repository.LivroRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;

    public List<LivroDTO> listarTodos() {
        return livroRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public LivroDTO buscarPorId(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
        return toDTO(livro);
    }

    @Transactional
    public LivroDTO criar(LivroDTO dto) {
        Livro livro = toEntity(dto);
        return toDTO(livroRepository.save(livro));
    }

    @Transactional
    public LivroDTO atualizar(Long id, LivroDTO dto) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));

        livro.setTitulo(dto.titulo());
        livro.setIsbn(dto.isbn());
        livro.setAnoPublicacao(dto.anoPublicacao());
        livro.setPreco(dto.preco());
        livro.setAutor(autorRepository.findById(dto.autorId())
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"))
        );
        livro.setCategoria(categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"))
        );

        return toDTO(livroRepository.save(livro));
    }

    public void deletar(Long id) {
        livroRepository.deleteById(id);
    }

    private LivroDTO toDTO(Livro livro) {
        return new LivroDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getIsbn(),
                livro.getAnoPublicacao(),
                livro.getPreco(),
                livro.getAutor().getId(),
                livro.getCategoria().getId()
        );
    }

    private Livro toEntity(LivroDTO dto) {
        Autor autor = autorRepository.findById(dto.autorId())
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        return Livro.builder()
                .titulo(dto.titulo())
                .isbn(dto.isbn())
                .anoPublicacao(dto.anoPublicacao())
                .preco(dto.preco())
                .autor(autor)
                .categoria(categoria)
                .build();
    }
}
