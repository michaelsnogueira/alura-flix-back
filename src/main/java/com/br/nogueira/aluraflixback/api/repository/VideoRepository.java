package com.br.nogueira.aluraflixback.api.repository;

import com.br.nogueira.aluraflixback.api.domain.model.Categoria;
import com.br.nogueira.aluraflixback.api.domain.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findByCategoria(Categoria categoria);
    List<Video> findByTitulo(String nome);
}

