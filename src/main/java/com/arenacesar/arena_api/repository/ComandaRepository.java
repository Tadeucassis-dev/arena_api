package com.arenacesar.arena_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arenacesar.arena_api.model.Comanda;

import java.util.List;

public interface ComandaRepository extends JpaRepository<Comanda, Long> {
    List<Comanda> findByStatus(String status);
}
