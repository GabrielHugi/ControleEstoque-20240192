package com.controleestoque.api_estoque.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import com.controleestoque.api_estoque.model.Estoque;
import com.controleestoque.api_estoque.model.Fornecedor;
import com.controleestoque.api_estoque.model.Produto;
import com.controleestoque.api_estoque.repository.ProdutoRepository;

import lombok.RequiredArgsConstructor;

import com.controleestoque.api_estoque.repository.CategoriaRepository;
import com.controleestoque.api_estoque.repository.EstoqueRepository;
import com.controleestoque.api_estoque.repository.FornecedorRepository;
import java.util.stream.Collectors;
import com.controleestoque.api_estoque.dto.ProdutoResponseDTO;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final FornecedorRepository fornecedorRepository;
    private final EstoqueRepository estoqueRepository;

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> getAllProdutos() {
        List<ProdutoResponseDTO> response = produtoRepository.findAll().stream()
            .map(produto -> {
                // busca a quantidade no estoque (0 se não achar)
                Integer quantidade = estoqueRepository.findByProduto(produto)
                    .map(Estoque::getQuantidade)
                    .orElse(0);

                // cria o dto
                return new ProdutoResponseDTO(
                    produto.getId(),
                    produto.getNome(),
                    produto.getPreco(),
                    produto.getCategoria(),
                    produto.getFornecedores(),
                    quantidade
                );
            })
            .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
        if (produto.getCategoria() == null || produto.getCategoria().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        categoriaRepository.findById(produto.getCategoria().getId())
            .ifPresent(produto::setCategoria);

        if (produto.getFornecedores() != null && !produto.getFornecedores().isEmpty()) {
            Set<Fornecedor> fornecedoresValidos = new HashSet<>();
            for (Fornecedor fornecedor : produto.getFornecedores()) {
                fornecedorRepository.findById(fornecedor.getId())
                    .ifPresent(fornecedoresValidos::add);
            }
            produto.setFornecedores(fornecedoresValidos);
        }

        Produto savedProduto = produtoRepository.save(produto);

        Estoque estoque = new Estoque();
        estoque.setProduto(savedProduto);
        estoque.setQuantidade(0);
        estoqueRepository.save(estoque);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produtoDetails) {
        return produtoRepository.findById(id)
            .map(produto -> {
                produto.setNome(produtoDetails.getNome());
                produto.setPreco(produtoDetails.getPreco());
                if (produtoDetails.getCategoria() != null && produtoDetails.getCategoria().getId() != null) {
                    categoriaRepository.findById(produtoDetails.getCategoria().getId())
                        .ifPresent(produto::setCategoria);
                }
                if (produtoDetails.getFornecedores() != null) {
                    Set<Fornecedor> fornecedoresValidos = new HashSet<>();
                    for (Fornecedor f : produtoDetails.getFornecedores()) {
                        fornecedorRepository.findById(f.getId()).ifPresent(fornecedoresValidos::add);
                    }
                    produto.setFornecedores(fornecedoresValidos);
                }
                Produto updatedProduto = produtoRepository.save(produto);
                return ResponseEntity.ok(updatedProduto);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/estoque")
    public ResponseEntity<?> atualizarEstoque(@PathVariable Long id, @RequestBody Map<String, Integer> payload) {
        Integer novaQuantidade = payload.get("quantidade");

        if (novaQuantidade == null || novaQuantidade < 0) {
            return ResponseEntity.badRequest().body("A quantidade deve ser informada e não pode ser negativa.");
        }

        var produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Produto produto = produtoOptional.get();

        var estoqueOptional = estoqueRepository.findByProduto(produto);
        if (estoqueOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Registro de estoque não encontrado para o produto ID: " + id);
        }

        Estoque estoque = estoqueOptional.get();
        estoque.setQuantidade(novaQuantidade);
        estoqueRepository.save(estoque);

        return ResponseEntity.ok(estoque);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduto(@PathVariable Long id) {
        if (!produtoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        try {
            produtoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            // se tiver uma venda que referencia esse produto essa venda tem que ser deletada primeiro
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Não é possível excluir o produto pois ele possui registros vinculados (ex: Vendas).");
        }
    }

}
