package com.br.nogueira.aluraflixback.api.domain.exception;

public class CategoriaNaoEncontradaException extends EntidadeNaoEncontradaException {
    public CategoriaNaoEncontradaException(String message) {
        super(message);
    }

    public CategoriaNaoEncontradaException(Long id) {
        this(String.format("Categoria de código %d não existe", id));
    }
}
