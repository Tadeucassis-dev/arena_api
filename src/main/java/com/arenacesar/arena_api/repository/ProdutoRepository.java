package com.arenacesar.arena_api.repository;

import com.arenacesar.arena_api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
