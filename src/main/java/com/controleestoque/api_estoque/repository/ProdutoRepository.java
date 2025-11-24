package com.controleestoque.api_estoque.repository;

import com.controleestoque.api_estoque.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByNome(String nome);

    List<Produto> findByNomeContaining(String nome);

    List<Produto> findByNomeStartingWith(String prefixo);

    List<Produto> findByNomeEndingWith(String sufixo);

    List<Produto> findByNomeIgnoreCase(String nome);

    List<Produto> findByNomeContainingIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);

    List<Produto> findByPreco(BigDecimal preco);

    List<Produto> findByPrecoLessThan(BigDecimal preco);

    List<Produto> findByPrecoLessThanEqual(BigDecimal preco);

    List<Produto> findByPrecoGreaterThan(BigDecimal preco);

    List<Produto> findByPrecoGreaterThanEqual(BigDecimal preco);

    List<Produto> findByPrecoBetween(BigDecimal min, BigDecimal max);

    List<Produto> findByCategoria_Id(Long categoriaId);

    boolean existsByCategoria_Id(Long categoriaId);

    List<Produto> findByFornecedores_Id(Long fornecedorId);

    boolean existsByFornecedores_Id(Long fornecedorId);

    List<Produto> findByEstoque_QuantidadeLessThanEqual(Integer quantidade);

    List<Produto> findByEstoque_QuantidadeLessThan(Integer quantidade);

    List<Produto> findByEstoque_QuantidadeGreaterThan(Integer quantidade);

    List<Produto> findAllByOrderByNomeAsc();

    List<Produto> findAllByOrderByNomeDesc();

    List<Produto> findAllByOrderByPrecoAsc();

    List<Produto> findAllByOrderByPrecoDesc();

    long countByCategoria_Id(Long categoriaId);

    long countByFornecedores_Id(Long fornecedorId);
}
