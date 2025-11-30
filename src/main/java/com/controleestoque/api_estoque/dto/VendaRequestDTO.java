package com.controleestoque.api_estoque.dto;

import java.util.List;

// o dto serve para permitir que o frontend mande só o id do cliente e não um objeto inteiro

public class VendaRequestDTO {
    private Long clienteId;
    private List<ItemVendaDTO> itens;

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public List<ItemVendaDTO> getItens() { return itens; }
    public void setItens(List<ItemVendaDTO> itens) { this.itens = itens; }
}