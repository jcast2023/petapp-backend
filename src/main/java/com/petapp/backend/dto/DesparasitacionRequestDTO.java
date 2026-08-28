package com.petapp.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class DesparasitacionRequestDTO {
    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @NotBlank(message = "El producto es obligatorio")
    private String producto;

    @NotNull(message = "La fecha de aplicación es obligatoria")
    private LocalDate fechaAplicacion;

    @NotNull(message = "La fecha de próxima dosis es obligatoria")
    private LocalDate fechaProximaDosis;

    private String notas;

    // Getters y Setters
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }

    public LocalDate getFechaAplicacion() { return fechaAplicacion; }
    public void setFechaAplicacion(LocalDate fechaAplicacion) { this.fechaAplicacion = fechaAplicacion; }

    public LocalDate getFechaProximaDosis() { return fechaProximaDosis; }
    public void setFechaProximaDosis(LocalDate fechaProximaDosis) { this.fechaProximaDosis = fechaProximaDosis; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
}
