package com.example.case_tecnico.biblioteca_digital.service;

import com.example.case_tecnico.biblioteca_digital.model.Autor;
import com.example.case_tecnico.biblioteca_digital.repository.AutorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public static class AutorService {
    private final AutorRepository autorRepository;

    public List<AutorDTO> listarTodos() {
        return autorRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public AutorDTO buscarPorId(Long id) {
        Autor autor = autorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
        return toDTO(autor);
    }

    @Transactional
    public AutorDTO criar(AutorDTO dto) {
        Autor autor = Autor.builder()
                .nome(dto.nome())
                .email(dto.email())
                .dataNascimento(dto.dataNascimento())
                .build();
        return toDTO(autorRepository.save(autor));
    }

    @Transactional
    public AutorDTO atualizar(Long id, AutorDTO dto) {
        Autor autor = autorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
        autor.setNome(dto.nome());
        autor.setEmail(dto.email());
        autor.setDataNascimento(dto.dataNascimento());
        return toDTO(autorRepository.save(autor));
    }

    public void deletar(Long id) {
        autorRepository.deleteById(id);
    }

    private AutorDTO toDTO(Autor autor) {
        return new AutorDTO(
                autor.getId(),
                autor.getNome(),
                autor.getEmail(),
                autor.getDataNascimento()
        );
    }
}

