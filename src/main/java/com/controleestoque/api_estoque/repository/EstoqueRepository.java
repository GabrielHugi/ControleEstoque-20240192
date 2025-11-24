package com.controleestoque.api_estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.controleestoque.api_estoque.model.Estoque;
import com.controleestoque.api_estoque.model.Produto;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    
    Optional<Estoque> findByProduto(Produto produto);

    Optional<Estoque> findByProdutoId(Long produtoId);

    boolean existsByProdutoId(Long produtoId);

    List<Estoque> findByQuantidade(Integer quantidade);

    List<Estoque> findByQuantidadeLessThan(Integer quantidade);

    List<Estoque> findByQuantidadeLessThanEqual(Integer quantidade);

    List<Estoque> findByQuantidadeGreaterThan(Integer quantidade);

    List<Estoque> findByQuantidadeGreaterThanEqual(Integer quantidade);

    List<Estoque> findByQuantidadeBetween(Integer min, Integer max);

    List<Estoque> findAllByOrderByQuantidadeAsc();

    List<Estoque> findAllByOrderByQuantidadeDesc();

    // estoque menor ou igual a quantidades
    List<Estoque> findByQuantidadeLessThanEqualOrderByQuantidadeAsc(Integer quantidade);

    long countByQuantidade(Integer quantidade);

    long countByQuantidadeLessThan(Integer quantidade);
}
