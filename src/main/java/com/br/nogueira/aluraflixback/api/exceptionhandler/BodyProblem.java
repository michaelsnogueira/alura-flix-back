package com.br.nogueira.aluraflixback.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class BodyProblem {
    private final Integer status;
    private final String type;
    private final String title;
    private final String detail;
    private final List<Field> fields;

    @Getter
    @Builder
    public static class Field {
        private final String name;
        private final String userMessage;
    }
}
