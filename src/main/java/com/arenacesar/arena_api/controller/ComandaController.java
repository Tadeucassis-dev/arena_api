package com.arenacesar.arena_api.controller;

import com.arenacesar.arena_api.model.ItemComanda;
import com.arenacesar.arena_api.service.ComandaService;
import com.arenacesar.arena_api.dtos.AdicionarItemRequest;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/comandas")
@CrossOrigin
public class ComandaController {

    private final ComandaService service;

    public ComandaController(ComandaService service) {
        this.service = service;
    }

    @PostMapping("/{id}/itens")
    public ItemComanda adicionarItem(@PathVariable UUID id, @RequestBody AdicionarItemRequest request) {
        return service.adicionarItem(id, request.produtoId, request.quantidade);
    }
}
