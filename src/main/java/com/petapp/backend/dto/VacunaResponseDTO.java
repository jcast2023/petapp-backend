package com.petapp.backend.dto;

import com.petapp.backend.model.Vacuna;
import java.time.LocalDate;

public class VacunaResponseDTO {
    private Long id;
    private String nombreVacuna;
    private LocalDate fechaAplicacion;
    private LocalDate fechaProximaDosis;
    private String veterinario;
    private String notas;
    private Long mascotaId;

    public VacunaResponseDTO(Vacuna vacuna) {
        this.id = vacuna.getId();
        this.nombreVacuna = vacuna.getNombreVacuna();
        this.fechaAplicacion = vacuna.getFechaAplicacion();
        this.fechaProximaDosis = vacuna.getFechaProximaDosis();
        this.veterinario = vacuna.getVeterinario();
        this.notas = vacuna.getNotas();
        this.mascotaId = vacuna.getMascota().getId();
    }

    // Getters
    public Long getId() { return id; }
    public String getNombreVacuna() { return nombreVacuna; }
    public LocalDate getFechaAplicacion() { return fechaAplicacion; }
    public LocalDate getFechaProximaDosis() { return fechaProximaDosis; }
    public String getVeterinario() { return veterinario; }
    public String getNotas() { return notas; }
    public Long getMascotaId() { return mascotaId; }
}