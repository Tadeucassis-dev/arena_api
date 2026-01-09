package com.arenacesar.arena_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.arenacesar.arena_api.model.Produto;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
}

