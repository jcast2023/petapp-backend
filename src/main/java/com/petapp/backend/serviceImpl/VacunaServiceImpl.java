package com.petapp.backend.serviceImpl;

import com.petapp.backend.model.Mascota;
import com.petapp.backend.model.Vacuna;
import com.petapp.backend.repository.MascotaRepository;
import com.petapp.backend.repository.VacunaRepository;
import com.petapp.backend.service.RecursoNoEncontradoException;
import com.petapp.backend.service.VacunaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacunaServiceImpl implements VacunaService {

    private final VacunaRepository vacunaRepository;
    private final MascotaRepository mascotaRepository;

    @Autowired
    public VacunaServiceImpl(VacunaRepository vacunaRepository, MascotaRepository mascotaRepository) {
        this.vacunaRepository = vacunaRepository;
        this.mascotaRepository = mascotaRepository;
    }

    @Override
    public Vacuna crear(Vacuna vacuna, Long mascotaId, Long propietarioId) {
        Mascota mascota = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una mascota con id " + mascotaId));

        if (!mascota.getPropietario().getId().equals(propietarioId)) {
            throw new RuntimeException("La mascota no pertenece al usuario autenticado");
        }

        vacuna.setMascota(mascota);
        return vacunaRepository.save(vacuna);
    }

    @Override
    public Vacuna obtenerPorId(Long id) {
        return vacunaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una vacuna con id " + id));
    }

    @Override
    public Vacuna obtenerPorIdYPropietario(Long id, Long propietarioId) {
        return vacunaRepository.findByIdAndPropietarioId(id, propietarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una vacuna con id " + id + " para este usuario"));
    }

    @Override
    public List<Vacuna> listarPorPropietario(Long propietarioId) {
        return vacunaRepository.findByPropietarioId(propietarioId);
    }

    @Override
    public Vacuna actualizar(Long id, Vacuna datosActualizados, Long propietarioId) {
        Vacuna existente = obtenerPorIdYPropietario(id, propietarioId);

        existente.setNombreVacuna(datosActualizados.getNombreVacuna());
        existente.setFechaAplicacion(datosActualizados.getFechaAplicacion());
        existente.setFechaProximaDosis(datosActualizados.getFechaProximaDosis());
        existente.setVeterinario(datosActualizados.getVeterinario());
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

        return vacunaRepository.save(existente);
    }

    @Override
    public void eliminar(Long id, Long propietarioId) {
        Vacuna existente = obtenerPorIdYPropietario(id, propietarioId);
        vacunaRepository.delete(existente);
    }
}