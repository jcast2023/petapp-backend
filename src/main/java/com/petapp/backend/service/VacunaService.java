package com.petapp.backend.service;

import com.petapp.backend.dto.VacunaRequestDTO;
import com.petapp.backend.model.Vacuna;
import java.util.List;

public interface VacunaService {
    Vacuna crear(VacunaRequestDTO request, Long mascotaId, Long propietarioId);
    Vacuna obtenerPorId(Long id);
    Vacuna obtenerPorIdYPropietario(Long id, Long propietarioId);
    List<Vacuna> listarPorPropietario(Long propietarioId);
    Vacuna actualizar(Long id, VacunaRequestDTO request, Long propietarioId);
    void eliminar(Long id, Long propietarioId);
}