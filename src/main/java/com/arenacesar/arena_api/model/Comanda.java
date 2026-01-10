package com.arenacesar.arena_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCliente;

    private String tipoCliente; // DAY_USE | ALUNO

    private BigDecimal valorDayUse;

    private BigDecimal valorTotal;

    private String status; // ABERTA | FECHADA

    private LocalDateTime dataAbertura;

    private LocalDateTime dataFechamento;
}
