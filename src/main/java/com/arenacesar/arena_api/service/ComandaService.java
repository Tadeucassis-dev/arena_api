package com.arenacesar.arena_api.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arenacesar.arena_api.model.Comanda;
import com.arenacesar.arena_api.model.ItemComanda;
import com.arenacesar.arena_api.model.Produto;
import com.arenacesar.arena_api.repository.ComandaRepository;
import com.arenacesar.arena_api.repository.ItemComandaRepository;
import com.arenacesar.arena_api.repository.ProdutoRepository;

@Service
public class ComandaService {

    private final ComandaRepository comandaRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemComandaRepository itemRepository;

    public ComandaService(
            ComandaRepository comandaRepository,
            ProdutoRepository produtoRepository,
            ItemComandaRepository itemRepository
    ) {
        this.comandaRepository = comandaRepository;
        this.produtoRepository = produtoRepository;
        this.itemRepository = itemRepository;
    }

    public Comanda fechar(Comanda comanda) {
        comanda.setStatus("FECHADA");
        comanda.setDataFechamento(LocalDateTime.now());
        return comandaRepository.save(comanda);
    }

    @Transactional
    public ItemComanda adicionarItem(Long comandaId, Long produtoId, Integer quantidade) {

        Comanda comanda = comandaRepository.findById(comandaId)
                .orElseThrow(() -> new RuntimeException("Comanda não encontrada"));

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (produto.getEstoque() == null || produto.getEstoque() < quantidade) {
            throw new IllegalStateException("Estoque insuficiente");
        }

        produto.setEstoque(produto.getEstoque() - quantidade);

        ItemComanda item = new ItemComanda();
        item.setComanda(comanda);
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setPrecoUnitario(produto.getPreco());

        BigDecimal subtotal = produto.getPreco()
                .multiply(BigDecimal.valueOf(quantidade));

        item.setSubtotal(subtotal);

        itemRepository.save(item);

        BigDecimal totalAtual = comanda.getValorTotal() != null
                ? comanda.getValorTotal()
                : BigDecimal.ZERO;

        comanda.setValorTotal(totalAtual.add(subtotal));
        comandaRepository.save(comanda);

        produtoRepository.save(produto);

        return item;
    }
}
