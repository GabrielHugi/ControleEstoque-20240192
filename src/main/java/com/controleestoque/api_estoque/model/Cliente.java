package com.controleestoque.api_estoque.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "tb_clientes")
public class Cliente {

    // o cliente não tem senha e etc porque não precisa adicionar no exercicio e ia ser muito trabalho

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    
    @Column(unique = true)
    private String cpf;

    private String email;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Venda> vendas;

    public Cliente() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<Venda> getVendas() { return vendas; }
    public void setVendas(List<Venda> vendas) { this.vendas = vendas; }
}