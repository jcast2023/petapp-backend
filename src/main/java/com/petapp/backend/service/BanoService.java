package com.petapp.backend.service;

import com.petapp.backend.dto.BanoRequestDTO;
import com.petapp.backend.model.Bano;
import java.util.List;

public interface BanoService {
    Bano crear(BanoRequestDTO request, Long mascotaId, Long propietarioId);
    Bano obtenerPorId(Long id);
    Bano obtenerPorIdYPropietario(Long id, Long propietarioId);
    List<Bano> listarPorPropietario(Long propietarioId);
    Bano actualizar(Long id, BanoRequestDTO request, Long propietarioId);
    void eliminar(Long id, Long propietarioId);
}