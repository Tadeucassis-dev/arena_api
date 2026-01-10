package com.arenacesar.arena_api.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter 
@Setter
public class ItemComanda {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Comanda comanda;

    @ManyToOne
    private Produto produto;

    private Integer quantidade;

    private BigDecimal precoUnitario;

    private BigDecimal subtotal;
}
