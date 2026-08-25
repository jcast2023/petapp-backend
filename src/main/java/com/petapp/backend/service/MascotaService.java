package com.petapp.backend.service;

import com.petapp.backend.model.Mascota;
import java.util.List;

public interface MascotaService {
    Mascota crear(Mascota mascota, Long propietarioId);
    Mascota obtenerPorId(Long id);
    Mascota obtenerPorIdYPropietario(Long id, Long propietarioId);
    List<Mascota> listarPorPropietario(Long propietarioId);
    Mascota actualizar(Long id, Mascota datosActualizados, Long propietarioId);
    void eliminar(Long id, Long propietarioId);
}