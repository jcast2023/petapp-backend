package com.petapp.backend.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class BanoRequestDTO {
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    private String notas;

    // Getters y Setters
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
}