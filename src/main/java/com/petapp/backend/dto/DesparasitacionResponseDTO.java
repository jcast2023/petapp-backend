package com.petapp.backend.dto;

import com.petapp.backend.model.Desparasitacion;
import java.time.LocalDate;

public class DesparasitacionResponseDTO {
    private Long id;
    private String tipo;
    private String producto;
    private LocalDate fechaAplicacion;
    private LocalDate fechaProximaDosis;
    private String notas;
    private Long mascotaId;

    public DesparasitacionResponseDTO(Desparasitacion desparasitacion) {
        this.id = desparasitacion.getId();
        this.tipo = desparasitacion.getTipo();
        this.producto = desparasitacion.getProducto();
        this.fechaAplicacion = desparasitacion.getFechaAplicacion();
        this.fechaProximaDosis = desparasitacion.getFechaProximaDosis();
        this.notas = desparasitacion.getNotas();
        this.mascotaId = desparasitacion.getMascota().getId();
    }

    // Getters
    public Long getId() { return id; }
    public String getTipo() { return tipo; }
    public String getProducto() { return producto; }
    public LocalDate getFechaAplicacion() { return fechaAplicacion; }
    public LocalDate getFechaProximaDosis() { return fechaProximaDosis; }
    public String getNotas() { return notas; }
    public Long getMascotaId() { return mascotaId; }
}