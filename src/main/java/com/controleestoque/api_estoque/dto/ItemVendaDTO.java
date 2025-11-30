package com.controleestoque.api_estoque.dto;

// o dto serve para permitir que o frontend mande só o id do produto e não um objeto inteiro

public class ItemVendaDTO {
    private Long produtoId;
    private Integer quantidade;

    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
}