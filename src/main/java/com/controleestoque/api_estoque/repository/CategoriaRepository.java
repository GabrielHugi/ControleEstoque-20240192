package com.controleestoque.api_estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.controleestoque.api_estoque.model.Categoria;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    Optional<Categoria> findByNome(String nome);

    List<Categoria> findByNomeContaining(String nome);

    List<Categoria> findByNomeStartingWith(String prefixo);

    List<Categoria> findByNomeEndingWith(String sufixo);

    Optional<Categoria> findByNomeIgnoreCase(String nome);

    List<Categoria> findByNomeContainingIgnoreCase(String nome);

    List<Categoria> findAllByOrderByNomeAsc();

    List<Categoria> findAllByOrderByNomeDesc();

    boolean existsByNome(String nome);

    long countByNome(String nome);

    List<Categoria> findByProdutosIsEmpty();

    List<Categoria> findByProdutosIsNotEmpty();

    List<Categoria> findByNomeLike(String pattern);
}
