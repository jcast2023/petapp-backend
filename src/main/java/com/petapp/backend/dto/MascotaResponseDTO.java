package com.petapp.backend.dto;

import com.petapp.backend.model.Mascota;
import java.time.LocalDate;

public class MascotaResponseDTO {
    private Long id;
    private String nombre;
    private String especie;
    private String raza;
    private String sexo;
    private LocalDate fechaNacimiento;
    private String fotoUrl;

    public MascotaResponseDTO(Mascota mascota) {
        this.id = mascota.getId();
        this.nombre = mascota.getNombre();
        this.especie = mascota.getEspecie();
        this.raza = mascota.getRaza();
        this.sexo = mascota.getSexo();
        this.fechaNacimiento = mascota.getFechaNacimiento();
        this.fotoUrl = mascota.getFotoUrl();
    }

    // Getters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEspecie() { return especie; }
    public String getRaza() { return raza; }
    public String getSexo() { return sexo; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getFotoUrl() { return fotoUrl; }
}