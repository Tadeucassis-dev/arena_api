package com.arenacesar.arena_api.controller;

import com.arenacesar.arena_api.dtos.ProdutoUpdateRequest;
import com.arenacesar.arena_api.model.Produto;
import com.arenacesar.arena_api.repository.ProdutoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin
public class ProdutoController {

    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Produto criar(@RequestBody Produto produto) {
        return repository.save(produto);
    }

    @GetMapping
    public List<Produto> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Produto buscar(@PathVariable Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

     @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @RequestBody ProdutoUpdateRequest req) {
        Produto p = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        if (req.getNome() != null) p.setNome(req.getNome());
        if (req.getPreco() != null) p.setPreco(req.getPreco());
        if (req.getEstoque() != null) {
            int novo = req.getEstoque();
            if (novo < 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estoque não pode ser negativo");
            p.setEstoque(novo);
        }
        return repository.save(p);
    }
}
