package com.br.nogueira.aluraflixback.api.controller;

import com.br.nogueira.aluraflixback.api.domain.model.Categoria;
import com.br.nogueira.aluraflixback.api.domain.model.Video;
import com.br.nogueira.aluraflixback.api.service.CategoriaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService service;

    private final Logger logger = LoggerFactory.getLogger(CategoriaControllerTest.class);

    @Test
    void retornar200AposChamadaAoEndPointListar() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/categorias"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void retornar200AposBuscarUmVideo() throws Exception {
        Categoria categoria = mockarUmaCategoria();
        Mockito.when(service.buscar(1L)).thenReturn(categoria);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/categorias/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("titulo").value("Cinema"));
    }

    @Test
    void retornar200AposBuscarVideosPorTitulo() throws Exception {
        Categoria categoria = mockarUmaCategoria();
        List<Video> videos = buscarVideos();

        Mockito.when(service.buscarVideosPorCategoria(categoria)).thenReturn(videos);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/categorias/1/videos"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void retornar201AposSalvarUmaCategoria() throws Exception {
        Categoria categoria = mockarUmaCategoria();
        String inputJson = mapToJson(categoria);

        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.post("/categorias")
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(inputJson))
                    .andDo(resultHandler -> logger.info(resultHandler.getResponse().getContentAsString()));

        result.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void retornar201AposAlterarUmaCategoria() throws Exception {
        Categoria categoria = mockarUmaCategoria();
        String inputJson = mapToJson(categoria);

        Mockito.when(service.buscar(1L)).thenReturn(categoria);

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.put("/categorias/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andDo(resultHandler -> logger.info("testeeee", resultHandler.getResponse().getContentAsString()));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void retornar204AposDeletarUmaCategoria() throws Exception {

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.delete("/categorias/1"))
                .andDo(MockMvcResultHandlers.print());

        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    private Categoria mockarUmaCategoria() {
        return Categoria.builder().id(1L).cor("#FFFFFF").titulo("Cinema").build();
    }

    private List<Categoria> buscarCategorias() {
        List<Categoria> categorias = new ArrayList<>();

        categorias.add(Categoria.builder().id(1L).cor("#FFFFFF").titulo("Cinema").build());
        categorias.add(Categoria.builder().id(2L).cor("#DDDDDD").titulo("Bar").build());
        return categorias;
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    private Video mockarUmVideo() {
        Categoria categoria = Categoria.builder().id(1L).cor("#FFFFFF").titulo("Cinema").build();
        return Video.builder().id(1L).titulo("Ragnar").descricao("Série que virou filme").url("teste").categoria(categoria).build();
    }

    private List<Video> buscarVideos() {
        List<Video> videos = new ArrayList<>();

        Categoria categoria = Categoria.builder().id(1L).cor("#FFFFFF").titulo("Cinema").build();
        videos.add(Video.builder().id(1L).titulo("Ragnar").descricao("Série que virou filme").categoria(categoria).build());
        videos.add(Video.builder().id(2L).titulo("Teste3").descricao("Teste3 descricao").categoria(categoria).build());
        return videos;
    }
}