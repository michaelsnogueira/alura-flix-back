package com.br.nogueira.aluraflixback.api.controller;

import com.br.nogueira.aluraflixback.api.domain.model.Video;
import com.br.nogueira.aluraflixback.api.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping
    public List<Video> listar() {
        return videoService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Video videoBanco = videoService.buscar(id);
        return ResponseEntity.ok(videoBanco);
    }

    @PostMapping
    public ResponseEntity<Video> salvar(@RequestBody @Valid Video video) {
        videoService.salvar(video);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody @Valid Video video) {
        Video videoRetorno = videoService.buscar(id);
        BeanUtils.copyProperties(video, videoRetorno, "id");
        videoService.salvar(videoRetorno);
        return ResponseEntity.ok(videoRetorno);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Video videoRetorno = videoService.buscar(id);
        videoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
