package com.example.case_tecnico.biblioteca_digital.service;

import com.example.case_tecnico.biblioteca_digital.dto.CategoriaDTO;
import com.example.case_tecnico.biblioteca_digital.model.Categoria;
import com.example.case_tecnico.biblioteca_digital.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public static class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public List<CategoriaDTO> listarTodas() {
        return categoriaRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public CategoriaDTO criar(CategoriaDTO dto) {
        Categoria categoria = Categoria.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .build();
        return toDTO(categoriaRepository.save(categoria));
    }

    public CategoriaDTO buscarPorId(Long id) {
        Categoria categoria = categoriaRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
        return toDTO(categoria);
    }

    private CategoriaDTO toDTO(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao()
        );
    }
}

