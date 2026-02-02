package com.arenacesar.arena_api.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Map<String, Object>> handleRse(ResponseStatusException ex) {
    return ResponseEntity.status(ex.getStatusCode()).body(Map.of(
      "timestamp", Instant.now().toString(),
      "status", ex.getStatusCode().value(),
      "error", ex.getReason() != null ? ex.getReason() : ex.getStatusCode().toString()
    ));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
      "timestamp", Instant.now().toString(),
      "status", 500,
      "error", "Erro interno"
    ));
  }
}