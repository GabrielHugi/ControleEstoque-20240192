package com.controleestoque.api_estoque.controller;

import com.controleestoque.api_estoque.model.Categoria;
import com.controleestoque.api_estoque.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    // get all
    @GetMapping
    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    // get by id
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long id) {
        return categoriaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // post
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria createCategoria(@RequestBody Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    // put by id
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateCategoria(
        @PathVariable Long id, @RequestBody Categoria categoriaDetails) {
        return categoriaRepository.findById(id)
            .map(categoria -> {
                categoria.setNome(categoriaDetails.getNome());
                Categoria updatedCategoria = categoriaRepository.save(categoria);
                return ResponseEntity.ok(updatedCategoria);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    // delete by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        if (!categoriaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        categoriaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}