package com.petapp.backend.serviceImpl;

import com.petapp.backend.model.Desparasitacion;
import com.petapp.backend.model.Mascota;
import com.petapp.backend.repository.DesparasitacionRepository;
import com.petapp.backend.repository.MascotaRepository;
import com.petapp.backend.service.DesparasitacionService;
import com.petapp.backend.service.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DesparasitacionServiceImpl implements DesparasitacionService {

    private final DesparasitacionRepository desparasitacionRepository;
    private final MascotaRepository mascotaRepository;

    @Autowired
    public DesparasitacionServiceImpl(DesparasitacionRepository desparasitacionRepository,
                                      MascotaRepository mascotaRepository) {
        this.desparasitacionRepository = desparasitacionRepository;
        this.mascotaRepository = mascotaRepository;
    }

    @Override
    @Transactional
    public Desparasitacion crear(Desparasitacion desparasitacion, Long mascotaId, Long propietarioId) {
        Mascota mascota = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una mascota con id " + mascotaId));

        if (!mascota.getPropietario().getId().equals(propietarioId)) {
            throw new RuntimeException("La mascota no pertenece al usuario autenticado");
        }

        desparasitacion.setMascota(mascota);
        Desparasitacion guardada = desparasitacionRepository.save(desparasitacion);
        System.out.println("✅ Desparasitación guardada: " + guardada.getProducto() + " - Mascota: " + guardada.getMascota().getNombre());
        return guardada;
    }

    @Override
    public Desparasitacion obtenerPorId(Long id) {
        return desparasitacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una desparasitación con id " + id));
    }

    @Override
    public Desparasitacion obtenerPorIdYPropietario(Long id, Long propietarioId) {
        return desparasitacionRepository.findByIdAndPropietarioId(id, propietarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una desparasitación con id " + id + " para este usuario"));
    }

    @Override
    public List<Desparasitacion> listarPorPropietario(Long propietarioId) {
        List<Desparasitacion> desparasitaciones = desparasitacionRepository.findByPropietarioId(propietarioId);
        System.out.println("📋 Desparasitaciones encontradas: " + desparasitaciones.size());
        for (Desparasitacion d : desparasitaciones) {
            System.out.println("  - " + d.getProducto() + " | Mascota: " +
                    (d.getMascota() != null ? d.getMascota().getNombre() : "null"));
        }
        return desparasitaciones;
    }

    @Override
    @Transactional
    public Desparasitacion actualizar(Long id, Desparasitacion datosActualizados, Long propietarioId) {
        Desparasitacion existente = obtenerPorIdYPropietario(id, propietarioId);

        existente.setTipo(datosActualizados.getTipo());
        existente.setProducto(datosActualizados.getProducto());
        existente.setFechaAplicacion(datosActualizados.getFechaAplicacion());
        existente.setFechaProximaDosis(datosActualizados.getFechaProximaDosis());
        existente.setNotas(datosActualizados.getNotas());

        if (datosActualizados.getMascota() != null && datosActualizados.getMascota().getId() != null) {
            Long nuevaMascotaId = datosActualizados.getMascota().getId();
            Mascota mascota = mascotaRepository.findById(nuevaMascotaId)
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "No existe una mascota con id " + nuevaMascotaId));

            if (!mascota.getPropietario().getId().equals(propietarioId)) {
                throw new RuntimeException("La mascota no pertenece al usuario autenticado");
            }
            existente.setMascota(mascota);
        }

        return desparasitacionRepository.save(existente);
    }

    @Override
    public void eliminar(Long id, Long propietarioId) {
        Desparasitacion existente = obtenerPorIdYPropietario(id, propietarioId);
        desparasitacionRepository.delete(existente);
    }
}