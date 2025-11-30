package com.controleestoque.api_estoque.service;

import com.controleestoque.api_estoque.dto.ItemVendaDTO;
import com.controleestoque.api_estoque.dto.VendaRequestDTO;
import com.controleestoque.api_estoque.model.*;
import com.controleestoque.api_estoque.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
// isso -->
@RequiredArgsConstructor
public class VendaService {

  // <-- faz com que isso fique automaticamente como argumentos da função
  private final VendaRepository vendaRepository;
  private final ProdutoRepository produtoRepository;
  private final ClienteRepository clienteRepository;

  @Transactional
  public Venda registrarVenda(VendaRequestDTO vendaDTO) {
    // verifica se o cliente existe
    Cliente cliente = clienteRepository.findById(vendaDTO.getClienteId())
      .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

    Venda venda = new Venda();
    venda.setCliente(cliente);
    List<ItemVenda> itensVenda = new ArrayList<>();
    BigDecimal totalVenda = BigDecimal.ZERO;

    // para cada item
    for (ItemVendaDTO itemDTO : vendaDTO.getItens()) {
      // acha o id do produto que foi vendido e vê se existe
      Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
        .orElseThrow(() -> new RuntimeException("Produto não encontrado ID: " + itemDTO.getProdutoId()));
      
      // ve o estoque e verifica se tem o suficiente e etc 
      Estoque estoque = produto.getEstoque();
            
      if (estoque == null) {
        throw new RuntimeException("Erro: Produto sem tabela de estoque vinculada.");
      }

      if (estoque.getQuantidade() < itemDTO.getQuantidade()) {
        throw new IllegalArgumentException("Estoque insuficiente para o produto: " + produto.getNome() + ". Disponível: " + estoque.getQuantidade());
      }

      // tira do estoque o que foi comprado
      estoque.setQuantidade(estoque.getQuantidade() - itemDTO.getQuantidade());

      // salva o produto com o estoque atualizado
      produtoRepository.save(produto);

      // cria um novo item venda, o dessa venda
      ItemVenda item = new ItemVenda();
      item.setVenda(venda);
      item.setProduto(produto);
      item.setQuantidade(itemDTO.getQuantidade());
      item.setPrecoUnitario(produto.getPreco());

      itensVenda.add(item);

      BigDecimal subtotal = produto.getPreco().multiply(new BigDecimal(itemDTO.getQuantidade()));
      totalVenda = totalVenda.add(subtotal);
    }

    // salva os bagulhos na venda
    venda.setItens(itensVenda);
    venda.setTotal(totalVenda);
    // salva a venda
    return vendaRepository.save(venda);
  }
}