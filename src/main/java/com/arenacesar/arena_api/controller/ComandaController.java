package com.arenacesar.arena_api.controller;

import com.arenacesar.arena_api.service.ComandaService;
import com.arenacesar.arena_api.dtos.AdicionarItemRequest;
import com.arenacesar.arena_api.model.ItemComanda;
import com.arenacesar.arena_api.model.Comanda;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/comandas")
@CrossOrigin
public class ComandaController {

    private final ComandaService service;

    public ComandaController(ComandaService service) {
        this.service = service;
    }

    @PostMapping("/{id}/itens")
    public ItemComanda adicionarItem(
            @PathVariable Long id,
            @RequestBody AdicionarItemRequest request
    ) {
        return service.adicionarItem(id, request.getProdutoId(), request.getQuantidade());
    }

    @GetMapping
    public List<Comanda> listar(@RequestParam(required = false) String status) {
        return status == null ? service.listar() : service.listarPorStatus(status);
    }

    @PostMapping
    public Comanda abrir(@RequestBody Comanda comanda) {
        return service.abrir(comanda);
    }

    @GetMapping("/{id}")
    public Comanda buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping("/{id}/fechar")
    public Comanda fechar(@PathVariable Long id) {
        return service.fechar(id);
    }
}
