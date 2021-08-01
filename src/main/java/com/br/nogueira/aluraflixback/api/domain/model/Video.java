package com.br.nogueira.aluraflixback.api.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "Não pode ser vazio ou nulo")
    private String titulo;

    @NotBlank(message = "Não pode ser vazio ou nulo")
    private String descricao;

    @NotBlank(message = "Não pode ser vazio ou nulo")
    private String url;

    @ManyToOne
    private Categoria categoria;

}
