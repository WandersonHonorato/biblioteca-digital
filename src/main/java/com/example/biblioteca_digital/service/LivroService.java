package com.example.biblioteca_digital.service;

import com.example.biblioteca_digital.database.Autor;
import com.example.biblioteca_digital.database.Categoria;
import com.example.biblioteca_digital.database.Livro;
import com.example.biblioteca_digital.dto.AutorDTO;
import com.example.biblioteca_digital.dto.CategoriaDTO;
import com.example.biblioteca_digital.dto.LivroDTO;
import com.example.biblioteca_digital.repository.AutorRepository;
import com.example.biblioteca_digital.repository.CategoriaRepository;
import com.example.biblioteca_digital.repository.LivroRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;

    public Page<LivroDTO> listarTodos(Pageable pageable) {
        return livroRepository.findAll(pageable)
                .map(this::converterParaDTO);
    }

    public LivroDTO buscarPorId(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado com ID: " + id));
        return converterParaDTO(livro);
    }

    public LivroDTO criar(LivroDTO livroDTO) {
        validarIsbnUnico(livroDTO.isbn(), null);
        validarRelacionamentos(livroDTO.autorId(), livroDTO.categoriaId());

        Livro livro = converterParaEntidade(livroDTO);
        Livro livroSalvo = livroRepository.save(livro);
        return converterParaDTO(livroSalvo);
    }

    public LivroDTO atualizar(Long id, LivroDTO livroDTO) {
        Livro livroExistente = livroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado com ID: " + id));

        validarIsbnUnico(livroDTO.isbn(), id);
        validarRelacionamentos(livroDTO.autorId(), livroDTO.categoriaId());

        atualizarCampos(livroExistente, livroDTO);

        Livro livroAtualizado = livroRepository.save(livroExistente);
        return converterParaDTO(livroAtualizado);
    }

    public void deletar(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado com ID: " + id));

        livroRepository.delete(livro);
    }

    public Page<LivroDTO> buscarComFiltros(Long categoriaId, Integer anoPublicacao, Long autorId, Pageable pageable) {
        return livroRepository.buscarComFiltros(categoriaId, anoPublicacao, autorId, pageable)
                .map(this::converterParaDTO);
    }

    public List<LivroDTO> buscarPorTitulo(String titulo) {
        List<Livro> livros = livroRepository.findByTituloContainingIgnoreCase(titulo);
        return livros.stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public Page<LivroDTO> buscarPorTitulo(String titulo, Pageable pageable) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo, pageable)
                .map(this::converterParaDTO);
    }

    public Optional<LivroDTO> buscarPorIsbn(String isbn) {
        return livroRepository.findByIsbn(isbn)
                .map(this::converterParaDTO);
    }

    public boolean existePorIsbn(String isbn) {
        return livroRepository.existsByIsbn(isbn);
    }

    private void validarIsbnUnico(String isbn, Long id) {
        Optional<Livro> livroExistente = livroRepository.findByIsbn(isbn);
        if (livroExistente.isPresent() && !livroExistente.get().getId().equals(id)) {
            throw new IllegalArgumentException("Já existe um livro com este ISBN: " + isbn);
        }
    }

    private void validarRelacionamentos(Long autorId, Long categoriaId) {
        if (autorRepository.findById(autorId).isEmpty()) {
            throw new EntityNotFoundException("Autor não encontrado com ID: " + autorId);
        }

        if (categoriaRepository.findById(categoriaId).isEmpty()) {
            throw new EntityNotFoundException("Categoria não encontrada com ID: " + categoriaId);
        }
    }

    private void atualizarCampos(Livro livroExistente, LivroDTO livroDTO) {
        livroExistente.setTitulo(livroDTO.titulo());
        livroExistente.setIsbn(livroDTO.isbn());
        livroExistente.setAnoPublicacao(livroDTO.anoPublicacao());
        livroExistente.setPreco(livroDTO.preco());
        livroExistente.setUrlOrigem(livroDTO.urlOrigem());

        if (!livroExistente.getAutor().getId().equals(livroDTO.autorId())) {
            Autor novoAutor = autorRepository.findById(livroDTO.autorId())
                    .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
            livroExistente.setAutor(novoAutor);
        }

        if (!livroExistente.getCategoria().getId().equals(livroDTO.categoriaId())) {
            Categoria novaCategoria = categoriaRepository.findById(livroDTO.categoriaId())
                    .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
            livroExistente.setCategoria(novaCategoria);
        }
    }

    private Integer contarLivrosPorAutor(Long autorId) {
        try {
            return Math.toIntExact(livroRepository.countByAutorId(autorId));
        } catch (Exception e) {
            return 0;
        }
    }

    private Integer contarLivrosPorCategoria(Long categoriaId) {
        try {
            return Math.toIntExact(livroRepository.countByCategoriaId(categoriaId));
        } catch (Exception e) {
            return 0;
        }
    }

    LivroDTO converterParaDTO(Livro livro) {

        AutorDTO autorDTO = new AutorDTO(
                livro.getAutor().getId(),
                livro.getAutor().getNome(),
                livro.getAutor().getEmail(),
                livro.getAutor().getDataNascimento(),
                livro.getAutor().getLivros().size()
        );

        CategoriaDTO categoriaDTO = new CategoriaDTO(
                livro.getCategoria().getId(),
                livro.getCategoria().getNome(),
                livro.getCategoria().getDescricao(),
                contarLivrosPorCategoria(livro.getCategoria().getId())
        );

        return new LivroDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getIsbn(),
                livro.getAnoPublicacao(),
                livro.getPreco(),
                livro.getAutor().getId(),
                livro.getCategoria().getId(),
                livro.getUrlOrigem(),
                livro.getDataCadastro(),
                livro.getDataAtualizacao(),
                autorDTO,
                categoriaDTO
        );
    }

        Livro converterParaEntidade (LivroDTO dto){

            Livro livro = Livro.builder().build();

            livro.setTitulo(dto.titulo());
            livro.setIsbn(dto.isbn());
            livro.setAnoPublicacao(dto.anoPublicacao());
            livro.setPreco(dto.preco());
            livro.setUrlOrigem(dto.urlOrigem()); // ou dto.urlOrigem() se renomear

            Autor autor = autorRepository.findById(dto.autorId())
                    .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
            livro.setAutor(autor);

            Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                    .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
            livro.setCategoria(categoria);

            return livro;
        }
    }