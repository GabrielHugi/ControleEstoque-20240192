package com.controleestoque.api_estoque.controller;

import com.controleestoque.api_estoque.dto.VendaRequestDTO;
import com.controleestoque.api_estoque.model.Venda;
import com.controleestoque.api_estoque.repository.VendaRepository;
import com.controleestoque.api_estoque.service.VendaService;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;
    private final VendaRepository vendaRepository;

    // post da venda
    @PostMapping
    public ResponseEntity<?> criarVenda(@RequestBody VendaRequestDTO vendaDTO) {
        try {
            Venda novaVenda = vendaService.registrarVenda(vendaDTO);
            return ResponseEntity.ok(novaVenda);
        } catch (IllegalArgumentException e) {
            // se não tiver estoque o suficiente
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao processar venda: " + e.getMessage());
        }
    }

    // get all
    @GetMapping
    public ResponseEntity<List<Venda>> listarVendas() {
        return ResponseEntity.ok(vendaRepository.findAll());
    }

    // get por id
    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarVendaPorId(@PathVariable Long id) {
        return vendaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVenda(@PathVariable Long id) {
        if (!vendaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        try {
            vendaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            // a venda não pode estar vinculada a nada atualmente mas mesmo assim fica aqui
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Não é possível excluir a venda pois existem registros dependentes vinculados a ela.");
        }
    }
}