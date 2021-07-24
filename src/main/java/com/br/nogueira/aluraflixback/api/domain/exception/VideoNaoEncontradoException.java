package com.br.nogueira.aluraflixback.api.domain.exception;

public class VideoNaoEncontradoException extends RuntimeException {
    public VideoNaoEncontradoException(String message) {
        super(message);
    }

    public VideoNaoEncontradoException(Long id) {
        this(String.format("Video de código %d não existe", id));
    }
}
