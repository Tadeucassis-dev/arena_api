package com.arenacesar.arena_api.service;

import com.arenacesar.arena_api.model.Comanda;
import com.arenacesar.arena_api.model.ItemComanda;
import com.arenacesar.arena_api.model.Produto;
import com.arenacesar.arena_api.repository.ComandaRepository;
import com.arenacesar.arena_api.repository.ItemComandaRepository;
import com.arenacesar.arena_api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ComandaService {

    private final ComandaRepository repository;
    private final ProdutoRepository produtoRepository;
    private final ItemComandaRepository itemRepository;

    public ComandaService(ComandaRepository repository,
                          ProdutoRepository produtoRepository,
                          ItemComandaRepository itemRepository) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
        this.itemRepository = itemRepository;
    }

    public Comanda fechar(Comanda comanda) {
        comanda.setStatus("FECHADA");
        comanda.setDataFechamento(LocalDateTime.now());
        return repository.save(comanda);
    }

    @Transactional
    public ItemComanda adicionarItem(UUID comandaId, UUID produtoId, Integer quantidade) {
        Comanda comanda = repository.findById(comandaId).orElseThrow();
        Produto produto = produtoRepository.findById(produtoId).orElseThrow();

        if (produto.getEstoque() == null || produto.getEstoque() < quantidade) {
            throw new IllegalStateException("Estoque insuficiente");
        }

        produto.setEstoque(produto.getEstoque() - quantidade);

        ItemComanda item = new ItemComanda();
        item.setComanda(comanda);
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setPrecoUnitario(produto.getPreco());
        BigDecimal subtotal = produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
        item.setSubtotal(subtotal);

        itemRepository.save(item);

        BigDecimal totalAtual = comanda.getValorTotal() != null ? comanda.getValorTotal() : BigDecimal.ZERO;
        comanda.setValorTotal(totalAtual.add(subtotal));
        repository.save(comanda);

        produtoRepository.save(produto);

        return item;
    }
}
