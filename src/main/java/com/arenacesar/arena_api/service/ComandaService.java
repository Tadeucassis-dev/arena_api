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
import java.util.List;
import com.arenacesar.arena_api.dtos.FechadasResumoResponse;
import com.arenacesar.arena_api.dtos.FechadasDiarioResponse;
import java.time.LocalDate;

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

    public List<Comanda> listar() {
        return comandaRepository.findAll();
    }

    public Comanda atualizar(Long id, Comanda atualizacao) {
        Comanda existente = comandaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comanda não encontrada"));
        existente.setNomeCliente(atualizacao.getNomeCliente());
        existente.setTipoCliente(atualizacao.getTipoCliente());
        if ("ABERTA".equals(existente.getStatus())) {
            existente.setValorDayUse(atualizacao.getValorDayUse());
        }
        return comandaRepository.save(existente);
    }

    public Comanda abrir(Comanda comanda) {
        comanda.setStatus("ABERTA");
        comanda.setDataAbertura(LocalDateTime.now());
        if (comanda.getValorTotal() == null) {
            comanda.setValorTotal(BigDecimal.ZERO);
        }
        return comandaRepository.save(comanda);
    }

    public Comanda buscar(Long id) {
        return comandaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comanda não encontrada"));
    }

    public List<Comanda> listarPorStatus(String status) {
        return comandaRepository.findByStatus(status);
    }

    public Comanda fechar(Long id) {
        Comanda comanda = comandaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comanda não encontrada"));
        comanda.setStatus("FECHADA");
        comanda.setDataFechamento(LocalDateTime.now());
        return comandaRepository.save(comanda);
    }

    public FechadasResumoResponse resumoFechadas(LocalDate from, LocalDate to) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime endExclusive = to.plusDays(1).atStartOfDay();
        List<Comanda> fechadas = comandaRepository.findByStatusAndDataFechamentoBetween("FECHADA", start, endExclusive);
        long count = fechadas.size();
        java.math.BigDecimal total = fechadas.stream()
                .map(Comanda::getValorTotal)
                .filter(java.util.Objects::nonNull)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
        java.math.BigDecimal ticketMedio = count > 0
                ? total.divide(java.math.BigDecimal.valueOf(count), java.math.RoundingMode.HALF_UP)
                : java.math.BigDecimal.ZERO;
        return new FechadasResumoResponse(total, count, ticketMedio);
    }

    public List<FechadasDiarioResponse> diarioFechadas(LocalDate from, LocalDate to) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime endExclusive = to.plusDays(1).atStartOfDay();
        List<Comanda> fechadas = comandaRepository.findByStatusAndDataFechamentoBetween("FECHADA", start, endExclusive);
        java.util.Map<java.time.LocalDate, java.math.BigDecimal> faturamentoPorDia = new java.util.HashMap<>();
        java.util.Map<java.time.LocalDate, java.lang.Long> qtdPorDia = new java.util.HashMap<>();
        for (Comanda c : fechadas) {
            java.time.LocalDate dia = c.getDataFechamento().toLocalDate();
            java.math.BigDecimal valor = c.getValorTotal() != null ? c.getValorTotal() : java.math.BigDecimal.ZERO;
            faturamentoPorDia.put(dia, faturamentoPorDia.getOrDefault(dia, java.math.BigDecimal.ZERO).add(valor));
            qtdPorDia.put(dia, qtdPorDia.getOrDefault(dia, 0L) + 1);
        }
        java.util.List<FechadasDiarioResponse> out = new java.util.ArrayList<>();
        for (java.time.LocalDate dia : faturamentoPorDia.keySet()) {
            out.add(new FechadasDiarioResponse(dia, qtdPorDia.getOrDefault(dia, 0L), faturamentoPorDia.getOrDefault(dia, java.math.BigDecimal.ZERO)));
        }
        out.sort(java.util.Comparator.comparing(FechadasDiarioResponse::getDia));
        return out;
    }

    @Transactional
    public void deletar(Long id) {
        itemRepository.deleteByComandaId(id);
        comandaRepository.deleteById(id);
    }

    public java.util.List<ItemComanda> listarItens(Long comandaId) {
        return itemRepository.findByComandaId(comandaId);
    }
}
