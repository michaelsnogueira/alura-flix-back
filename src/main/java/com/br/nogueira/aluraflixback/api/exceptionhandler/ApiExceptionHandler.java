package com.br.nogueira.aluraflixback.api.exceptionhandler;

import com.br.nogueira.aluraflixback.api.domain.exception.EntidadeNaoEncontradaException;
import com.br.nogueira.aluraflixback.api.domain.exception.VideoNaoEncontradoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException e, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        BodyProblem bodyProblem = BodyProblem.builder()
                .detail(e.getMessage())
                .title(status.getReasonPhrase())
                .status(status.value())
                .type("Recurso não encontrado")
                .build();

        return handleExceptionInternal(e, bodyProblem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException e, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        BodyProblem bodyProblem = BodyProblem.builder()
                .detail("Não é possível deletar o registro pois o mesmo encontra-se em uso em outra tabela")
                .title("ERROR-CONSTRAINT")
                .status(status.value())
                .type("Erro de constaint")
                .build();
        return handleExceptionInternal(e, bodyProblem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<BodyProblem.Field> fields = ex.getBindingResult().getFieldErrors().stream()
                .map(errors ->
                {
                    return BodyProblem.Field.builder().name(errors.getField()).userMessage(errors.getDefaultMessage()).build();
                }).collect(Collectors.toList());

        BodyProblem bodyProblem = BodyProblem.builder()
                .detail("Um ou mais campos estão inválidos")
                .title("CAMPOS-INVÁLIDOS")
                .status(status.value())
                .type("Campos").fields(fields)
                .build();
        return handleExceptionInternal(ex, bodyProblem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
