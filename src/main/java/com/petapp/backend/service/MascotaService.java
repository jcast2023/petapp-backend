package com.petapp.backend.service;

import com.petapp.backend.dto.MascotaRequestDTO;
import com.petapp.backend.model.Mascota;
import java.util.List;

public interface MascotaService {
    Mascota crear(MascotaRequestDTO request, Long usuarioId);
    Mascota obtenerPorId(Long id);
    Mascota obtenerPorIdYPropietario(Long id, Long propietarioId);
    List<Mascota> listarPorPropietario(Long propietarioId);
    Mascota actualizar(Long id, MascotaRequestDTO request, Long usuarioId);
    void eliminar(Long id, Long propietarioId);
}