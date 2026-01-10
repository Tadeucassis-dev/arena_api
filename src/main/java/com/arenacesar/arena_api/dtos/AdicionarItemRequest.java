package com.arenacesar.arena_api.dtos;

public class AdicionarItemRequest {

    private Long produtoId;
    private Integer quantidade;

    public Long getProdutoId() {
        return produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
}
