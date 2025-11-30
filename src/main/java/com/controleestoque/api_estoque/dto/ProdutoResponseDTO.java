package com.controleestoque.api_estoque.dto;

import java.math.BigDecimal;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.controleestoque.api_estoque.model.Categoria;
import com.controleestoque.api_estoque.model.Fornecedor;
import com.controleestoque.api_estoque.model.Produto;

// esse dto serve para formatar o output para que fique melhor e para consertar também o loop do json

public class ProdutoResponseDTO {
    private Long id;
    private String nome;
    private BigDecimal preco; 

    // essas anotações são para que não faça um loop de json infinito
    @JsonIgnoreProperties("produtos")
    private Categoria categoria;

    // essa também
    @JsonIgnoreProperties("produtos")
    private Set<Fornecedor> fornecedores;
    
    private Integer quantidade;

    // construtor default
    public ProdutoResponseDTO() {
    }

    // construtor com produto
    public ProdutoResponseDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.preco = produto.getPreco();
        this.categoria = produto.getCategoria();
        this.fornecedores = produto.getFornecedores();
        
        // coloca só a quantidade em vez do objeto estoque
        if (produto.getEstoque() != null) {
            this.quantidade = produto.getEstoque().getQuantidade();
        }
    }

    // construtor com as propriedades
    public ProdutoResponseDTO(Long id, String nome, BigDecimal preco, Categoria categoria, Set<Fornecedor> fornecedores, Integer quantidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
        this.fornecedores = fornecedores;
        this.quantidade = quantidade;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public Set<Fornecedor> getFornecedores() { return fornecedores; }
    public void setFornecedores(Set<Fornecedor> fornecedores) { this.fornecedores = fornecedores; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
}