package com.petapp.backend.dto;

import com.petapp.backend.model.HistorialMedico;
import java.time.LocalDate;

public class HistorialMedicoResponseDTO {
    private Long id;
    private LocalDate fecha;
    private String motivoConsulta;
    private String diagnostico;
    private String tratamiento;
    private String veterinario;
    private String clinica;
    private Long mascotaId;

    public HistorialMedicoResponseDTO(HistorialMedico historial) {
        this.id = historial.getId();
        this.fecha = historial.getFecha();
        this.motivoConsulta = historial.getMotivoConsulta();
        this.diagnostico = historial.getDiagnostico();
        this.tratamiento = historial.getTratamiento();
        this.veterinario = historial.getVeterinario();
        this.clinica = historial.getClinica();
        this.mascotaId = historial.getMascota().getId();
    }

    // Getters
    public Long getId() { return id; }
    public LocalDate getFecha() { return fecha; }
    public String getMotivoConsulta() { return motivoConsulta; }
    public String getDiagnostico() { return diagnostico; }
    public String getTratamiento() { return tratamiento; }
    public String getVeterinario() { return veterinario; }
    public String getClinica() { return clinica; }
    public Long getMascotaId() { return mascotaId; }
}