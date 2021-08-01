package com.br.nogueira.aluraflixback.api.domain.model;

import com.br.nogueira.aluraflixback.core.validation.Cor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Categoria {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Não pode ser vazio ou nulo")
    private String titulo;

    @Cor()
    private String cor;

    @JsonIgnore
    @OneToMany(mappedBy = "categoria")
    private List<Video> videos;

}
