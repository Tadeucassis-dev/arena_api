package com.arenacesar.arena_api.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
public class Comanda {

    @Id
    @GeneratedValue
    private UUID id;

    private String nomeCliente;

    private String tipoCliente; // DAY_USE | ALUNO

    private BigDecimal valorDayUse;

    private BigDecimal valorTotal;

    private String status; // ABERTA | FECHADA

    private LocalDateTime dataAbertura;

    private LocalDateTime dataFechamento;
}
