package com.petapp.backend.service;

import com.petapp.backend.model.Peso;
import java.util.List;

public interface PesoService {
    Peso crear(Peso peso, Long mascotaId, Long propietarioId);
    Peso obtenerPorId(Long id);
    Peso obtenerPorIdYPropietario(Long id, Long propietarioId);
    List<Peso> listarPorPropietario(Long propietarioId);
    Peso actualizar(Long id, Peso datosActualizados, Long propietarioId);
    void eliminar(Long id, Long propietarioId);
}