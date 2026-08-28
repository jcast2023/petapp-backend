package com.petapp.backend.service;

import com.petapp.backend.dto.PesoRequestDTO;
import com.petapp.backend.model.Peso;
import java.util.List;

public interface PesoService {
    Peso crear(PesoRequestDTO request, Long mascotaId, Long propietarioId);
    Peso obtenerPorId(Long id);
    Peso obtenerPorIdYPropietario(Long id, Long propietarioId);
    List<Peso> listarPorPropietario(Long propietarioId);
    Peso actualizar(Long id, PesoRequestDTO request, Long propietarioId);
    void eliminar(Long id, Long propietarioId);
}