package com.arenacesar.arena_api.dtos;

import java.math.BigDecimal;

public class ProdutoUpdateRequest {
    private String nome;
    private BigDecimal preco;
    private Integer estoque;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public Integer getEstoque() { return estoque; }
    public void setEstoque(Integer estoque) { this.estoque = estoque; }
}