package com.br.nogueira.aluraflixback.api.repository;

import com.br.nogueira.aluraflixback.api.domain.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
