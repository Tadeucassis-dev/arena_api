package com.arenacesar.arena_api.controller;

import com.arenacesar.arena_api.service.ComandaService;
import com.arenacesar.arena_api.dtos.AdicionarItemRequest;
import com.arenacesar.arena_api.model.ItemComanda;
import com.arenacesar.arena_api.model.Comanda;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import com.arenacesar.arena_api.dtos.FechadasResumoResponse;
import com.arenacesar.arena_api.dtos.FechadasDiarioResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

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

    @GetMapping("/fechadas/total")
    public FechadasResumoResponse totalFechadas(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        return service.resumoFechadas(from, to);
    }

    @GetMapping("/fechadas/diario")
    public java.util.List<FechadasDiarioResponse> diarioFechadas(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        return service.diarioFechadas(from, to);
    }
}

    @PutMapping("/{id}")
    public Comanda atualizar(@PathVariable Long id, @RequestBody Comanda atualizacao) {
        return service.atualizar(id, atualizacao);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id, @RequestParam(name = "confirmar", defaultValue = "false") boolean confirmar) {
        if (!confirmar) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Confirme a exclusão com ?confirmar=true");
        }
        service.deletar(id);
    }
}
