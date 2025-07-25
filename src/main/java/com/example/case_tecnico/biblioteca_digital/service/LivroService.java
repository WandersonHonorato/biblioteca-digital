package com.example.case_tecnico.biblioteca_digital.service;

import com.example.case_tecnico.biblioteca_digital.dto.LivroDTO;
import com.example.case_tecnico.biblioteca_digital.model.Livro;
import com.example.case_tecnico.biblioteca_digital.repository.LivroRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    private LivroRepository livroRepository;

    public LivroDTO criar(LivroDTO dto) {
        if (livroRepository.existsByIsbn(dto.isbn())) {
            throw new LivroJaExisteException("ISBN já cadastrado");
        }

        Livro livro = new Livro();
        livro.setTitulo(dto.titulo());
        livro.setIsbn(dto.isbn());
        livro.setPreco(dto.preco());
        livro.setAnoPublicacao(dto.anoPublicacao());

        livroRepository.save(livro);
        return dto;
    }

    public List<LivroDTO> buscarPorTitulo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo)
                .stream()
                .map(l -> new LivroDTO(l.getTitulo(),
                        l.getIsbn(),
                        l.getPreco(),
                        l.getAnoPublicacao()))
                .toList();
    }
}
