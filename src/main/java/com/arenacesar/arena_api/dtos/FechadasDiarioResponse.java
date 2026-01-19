package com.arenacesar.arena_api.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FechadasDiarioResponse {
  private final LocalDate dia;
  private final long qtd;
  private final BigDecimal faturamento;

  public FechadasDiarioResponse(LocalDate dia, long qtd, BigDecimal faturamento) {
    this.dia = dia;
    this.qtd = qtd;
    this.faturamento = faturamento;
  }

  public LocalDate getDia() { return dia; }
  public long getQtd() { return qtd; }
  public BigDecimal getFaturamento() { return faturamento; }
}