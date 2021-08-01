package com.br.nogueira.aluraflixback.api.service;


import com.br.nogueira.aluraflixback.api.domain.exception.CategoriaNaoEncontradaException;
import com.br.nogueira.aluraflixback.api.domain.model.Categoria;
import com.br.nogueira.aluraflixback.api.domain.model.Video;
import com.br.nogueira.aluraflixback.api.repository.CategoriaRepository;
import com.br.nogueira.aluraflixback.api.repository.VideoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final VideoRepository videoRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, VideoRepository videoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.videoRepository = videoRepository;
    }

    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    public Categoria buscar(Long id) {
        return categoriaRepository.findById(id).orElseThrow(() -> new CategoriaNaoEncontradaException(id));
    }

    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void deletar(Categoria categoria) {
        categoriaRepository.delete(categoria);
    }

    public List<Video> buscarVideosPorCategoria(Categoria categoriaBanco) {
        return videoRepository.findByCategoria(categoriaBanco);
    }
}
