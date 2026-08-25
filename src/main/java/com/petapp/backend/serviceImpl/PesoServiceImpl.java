package com.petapp.backend.serviceImpl;

import com.petapp.backend.model.Mascota;
import com.petapp.backend.model.Peso;
import com.petapp.backend.repository.MascotaRepository;
import com.petapp.backend.repository.PesoRepository;
import com.petapp.backend.service.PesoService;
import com.petapp.backend.service.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PesoServiceImpl implements PesoService {

    private final PesoRepository pesoRepository;
    private final MascotaRepository mascotaRepository;

    @Autowired
    public PesoServiceImpl(PesoRepository pesoRepository, MascotaRepository mascotaRepository) {
        this.pesoRepository = pesoRepository;
        this.mascotaRepository = mascotaRepository;
    }

    @Override
    @Transactional
    public Peso crear(Peso peso, Long mascotaId, Long propietarioId) {
        Mascota mascota = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una mascota con id " + mascotaId));

        if (!mascota.getPropietario().getId().equals(propietarioId)) {
            throw new RuntimeException("La mascota no pertenece al usuario autenticado");
        }

        peso.setMascota(mascota);
        Peso guardado = pesoRepository.save(peso);
        System.out.println("✅ Registro de peso guardado: " + guardado.getPesoKg() + " kg - Mascota: " + guardado.getMascota().getNombre());
        return guardado;
    }

    @Override
    public Peso obtenerPorId(Long id) {
        return pesoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un registro de peso con id " + id));
    }

    @Override
    public Peso obtenerPorIdYPropietario(Long id, Long propietarioId) {
        return pesoRepository.findByIdAndPropietarioId(id, propietarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un registro de peso con id " + id + " para este usuario"));
    }

    @Override
    public List<Peso> listarPorPropietario(Long propietarioId) {
        List<Peso> pesos = pesoRepository.findByPropietarioId(propietarioId);
        System.out.println("📋 Registros de peso encontrados: " + pesos.size());
        for (Peso p : pesos) {
            System.out.println("  - " + p.getPesoKg() + " kg | Mascota: " +
                    (p.getMascota() != null ? p.getMascota().getNombre() : "null"));
        }
        return pesos;
    }

    @Override
    @Transactional
    public Peso actualizar(Long id, Peso datosActualizados, Long propietarioId) {
        Peso existente = obtenerPorIdYPropietario(id, propietarioId);

        existente.setFecha(datosActualizados.getFecha());
        existente.setPesoKg(datosActualizados.getPesoKg());

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

        return pesoRepository.save(existente);
    }

    @Override
    public void eliminar(Long id, Long propietarioId) {
        Peso existente = obtenerPorIdYPropietario(id, propietarioId);
        pesoRepository.delete(existente);
    }
}