package com.arenacesar.arena_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arenacesar.arena_api.model.ItemComanda;

import java.util.UUID;

public interface ItemComandaRepository extends JpaRepository<ItemComanda, UUID> {
}
