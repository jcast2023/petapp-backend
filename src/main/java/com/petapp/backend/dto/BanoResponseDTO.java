package com.petapp.backend.dto;

import com.petapp.backend.model.Bano;
import java.time.LocalDate;

public class BanoResponseDTO {
    private Long id;
    private LocalDate fecha;
    private String notas;
    private Long mascotaId;

    public BanoResponseDTO(Bano bano) {
        this.id = bano.getId();
        this.fecha = bano.getFecha();
        this.notas = bano.getNotas();
        this.mascotaId = bano.getMascota().getId();
    }

    // Getters
    public Long getId() { return id; }
    public LocalDate getFecha() { return fecha; }
    public String getNotas() { return notas; }
    public Long getMascotaId() { return mascotaId; }
}