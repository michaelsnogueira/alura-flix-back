package com.br.nogueira.aluraflixback.api.controller;

import com.br.nogueira.aluraflixback.api.domain.model.Categoria;
import com.br.nogueira.aluraflixback.api.domain.model.Video;
import com.br.nogueira.aluraflixback.api.service.CategoriaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> listar() {
        return categoriaService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Categoria categoriaBanco = categoriaService.buscar(id);
        return ResponseEntity.ok(categoriaBanco);
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Valid Categoria categoria) {
        Categoria categoriaBanco =  categoriaService.salvar(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaBanco);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody @Valid Categoria categoria) {
        Categoria categoriaBanco = categoriaService.buscar(id);
        BeanUtils.copyProperties(categoria, categoriaBanco, "id");
        categoriaService.salvar(categoriaBanco);
        return ResponseEntity.ok(categoriaBanco);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Categoria categoriaBanco = categoriaService.buscar(id);
        categoriaService.deletar(categoriaBanco);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}/videos")
    public ResponseEntity<?> buscarVideosPorCategoria(@PathVariable Long id) {
        Categoria categoriaBanco = categoriaService.buscar(id);
        List<Video> videos = categoriaService.buscarVideosPorCategoria(categoriaBanco);
        return ResponseEntity.ok(videos);
    }
}
