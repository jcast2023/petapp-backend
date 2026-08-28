package com.petapp.backend.serviceImpl;

import com.petapp.backend.dto.VacunaRequestDTO;
import com.petapp.backend.model.Mascota;
import com.petapp.backend.model.Vacuna;
import com.petapp.backend.repository.MascotaRepository;
import com.petapp.backend.repository.VacunaRepository;
import com.petapp.backend.service.VacunaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public Vacuna crear(VacunaRequestDTO request, Long mascotaId, Long propietarioId) {
        Mascota mascota = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mascota no encontrada"));

        // Check: El propietario debe ser el dueño de la mascota
        if (!mascota.getPropietario().getId().equals(propietarioId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para esta mascota");
        }

        Vacuna vacuna = new Vacuna();
        vacuna.setNombreVacuna(request.getNombreVacuna());
        vacuna.setFechaAplicacion(request.getFechaAplicacion());
        vacuna.setFechaProximaDosis(request.getFechaProximaDosis());
        vacuna.setVeterinario(request.getVeterinario());
        vacuna.setNotas(request.getNotas());
        vacuna.setMascota(mascota);
        return vacunaRepository.save(vacuna);
    }

    @Override
    public Vacuna obtenerPorId(Long id) {
        return vacunaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vacuna no encontrada"));
    }

    @Override
    public Vacuna obtenerPorIdYPropietario(Long id, Long propietarioId) {
        return vacunaRepository.findByIdAndPropietarioId(id, propietarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vacuna no encontrada"));
    }

    @Override
    public List<Vacuna> listarPorPropietario(Long propietarioId) {
        return vacunaRepository.findByPropietarioId(propietarioId);
    }

    @Override
    public Vacuna actualizar(Long id, VacunaRequestDTO request, Long propietarioId) {
        Vacuna vacuna = obtenerPorIdYPropietario(id, propietarioId);
        vacuna.setNombreVacuna(request.getNombreVacuna());
        vacuna.setFechaAplicacion(request.getFechaAplicacion());
        vacuna.setFechaProximaDosis(request.getFechaProximaDosis());
        vacuna.setVeterinario(request.getVeterinario());
        vacuna.setNotas(request.getNotas());
        return vacunaRepository.save(vacuna);
    }

    @Override
    public void eliminar(Long id, Long propietarioId) {
        Vacuna vacuna = obtenerPorIdYPropietario(id, propietarioId);
        vacunaRepository.delete(vacuna);
    }
}