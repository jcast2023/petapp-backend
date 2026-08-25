package com.petapp.backend.service;

import com.petapp.backend.model.Desparasitacion;
import java.util.List;

public interface DesparasitacionService {
    Desparasitacion crear(Desparasitacion desparasitacion, Long mascotaId, Long propietarioId);
    Desparasitacion obtenerPorId(Long id);
    Desparasitacion obtenerPorIdYPropietario(Long id, Long propietarioId);
    List<Desparasitacion> listarPorPropietario(Long propietarioId);
    Desparasitacion actualizar(Long id, Desparasitacion datosActualizados, Long propietarioId);
    void eliminar(Long id, Long propietarioId);
}