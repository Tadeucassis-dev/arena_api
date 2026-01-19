package com.arenacesar.arena_api.dtos;

import java.math.BigDecimal;

public class FechadasResumoResponse {
  private final BigDecimal total;
  private final long count;
  private final BigDecimal ticketMedio;

  public FechadasResumoResponse(BigDecimal total, long count, BigDecimal ticketMedio) {
    this.total = total;
    this.count = count;
    this.ticketMedio = ticketMedio;
  }

  public BigDecimal getTotal() { return total; }
  public long getCount() { return count; }
  public BigDecimal getTicketMedio() { return ticketMedio; }
}