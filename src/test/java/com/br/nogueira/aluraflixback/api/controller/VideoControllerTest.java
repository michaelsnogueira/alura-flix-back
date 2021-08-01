package com.br.nogueira.aluraflixback.api.controller;

import com.br.nogueira.aluraflixback.api.domain.model.Categoria;
import com.br.nogueira.aluraflixback.api.domain.model.Video;
import com.br.nogueira.aluraflixback.api.service.VideoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VideoService service;

    private final Logger logger = LoggerFactory.getLogger(VideoControllerTest.class);

    @Test
    void retornar200AposChamadaAoEndPointListar() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/videos"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void retornar200AposBuscarUmVideo() throws Exception {
        Video video = mockarUmVideo();
        Mockito.when(service.buscar(1L)).thenReturn(video);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/videos/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("titulo").value("Ragnar"));
    }

    @Test
    void retornar200AposBuscarVideosPorTitulo() throws Exception {
        List<Video> video = buscarVideos();
        Mockito.when(service.buscarPorNome("Ragnar")).thenReturn(video);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/videos/consultar-por-titulo?search=Ragnar"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"));
    }

    @Test
    void retornar201AposSalvarUmVideo() throws Exception {
        Video video = mockarUmVideo();
        String inputJson = mapToJson(video);

        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.post("/videos")
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(inputJson))
                    .andDo(resultHandler -> logger.info(resultHandler.getResponse().getContentAsString()));

        result.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void alterar() throws Exception {
        Video video = mockarUmVideo();
        String inputJson = mapToJson(video);

        Mockito.when(service.buscar(1L)).thenReturn(video);

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.put("/videos/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andDo(resultHandler -> logger.info("testeeee", resultHandler.getResponse().getContentAsString()));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void deletar() throws Exception {

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.delete("/videos/1"))
                .andDo(MockMvcResultHandlers.print());

        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
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

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}