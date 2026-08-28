package com.petapp.backend.dto;

import com.petapp.backend.model.Peso;
import java.time.LocalDate;

public class PesoResponseDTO {
    private Long id;
    private LocalDate fecha;
    private Double pesoKg;
    private Long mascotaId;

    public PesoResponseDTO(Peso peso) {
        this.id = peso.getId();
        this.fecha = peso.getFecha();
        this.pesoKg = peso.getPesoKg();
        this.mascotaId = peso.getMascota().getId();
    }

    // Getters
    public Long getId() { return id; }
    public LocalDate getFecha() { return fecha; }
    public Double getPesoKg() { return pesoKg; }
    public Long getMascotaId() { return mascotaId; }
}