package com.br.nogueira.aluraflixback.api.service;

import com.br.nogueira.aluraflixback.api.domain.exception.VideoNaoEncontradoException;
import com.br.nogueira.aluraflixback.api.domain.model.Video;
import com.br.nogueira.aluraflixback.api.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public List<Video> listar() {
        return videoRepository.findAll();
    }

    public Video buscar(Long id) {
        return videoRepository.findById(id).orElseThrow(() -> new VideoNaoEncontradoException(id));
    }

    public void salvar(Video video) {
        videoRepository.save(video);
    }

    public void deletar(Long id) {
        videoRepository.deleteById(id);
    }

    public List<Video> buscarPorNome(String search) {
        return videoRepository.findByTitulo(search);
    }
}
