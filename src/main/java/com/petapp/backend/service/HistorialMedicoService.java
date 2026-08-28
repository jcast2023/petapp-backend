package com.petapp.backend.service;

import com.petapp.backend.dto.HistorialMedicoRequestDTO;
import com.petapp.backend.model.HistorialMedico;
import java.util.List;

public interface HistorialMedicoService {
    HistorialMedico crear(HistorialMedicoRequestDTO request, Long mascotaId, Long propietarioId);
    HistorialMedico obtenerPorId(Long id);
    HistorialMedico obtenerPorIdYPropietario(Long id, Long propietarioId);
    List<HistorialMedico> listarPorPropietario(Long propietarioId);
    HistorialMedico actualizar(Long id, HistorialMedicoRequestDTO request, Long propietarioId);
    void eliminar(Long id, Long propietarioId);
}