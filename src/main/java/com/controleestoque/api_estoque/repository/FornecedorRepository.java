package com.controleestoque.api_estoque.repository;

import com.controleestoque.api_estoque.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    Optional<Fornecedor> findByNome(String nome);

    List<Fornecedor> findByNomeOrderByNomeAsc(String nome);

    List<Fornecedor> findByNomeContaining(String nome);

    List<Fornecedor> findByNomeStartingWith(String prefixo);

    List<Fornecedor> findByNomeEndingWith(String sufixo);

    Optional<Fornecedor> findByNomeIgnoreCase(String nome);

    List<Fornecedor> findByNomeContainingIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);

    List<Fornecedor> findAllByOrderByNomeAsc();

    List<Fornecedor> findAllByOrderByNomeDesc();

    List<Fornecedor> findByProdutosIsEmpty();

    List<Fornecedor> findByProdutosIsNotEmpty();

    List<Fornecedor> findByProdutos_Id(Long produtoId);

    boolean existsByProdutos_Id(Long produtoId);
}
