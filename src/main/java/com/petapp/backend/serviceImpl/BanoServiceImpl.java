package com.petapp.backend.serviceImpl;

import com.petapp.backend.model.Bano;
import com.petapp.backend.model.Mascota;
import com.petapp.backend.repository.BanoRepository;
import com.petapp.backend.repository.MascotaRepository;
import com.petapp.backend.service.BanoService;
import com.petapp.backend.service.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BanoServiceImpl implements BanoService {

    private final BanoRepository banoRepository;
    private final MascotaRepository mascotaRepository;

    @Autowired
    public BanoServiceImpl(BanoRepository banoRepository, MascotaRepository mascotaRepository) {
        this.banoRepository = banoRepository;
        this.mascotaRepository = mascotaRepository;
    }

    @Override
    public Bano crear(Bano bano, Long mascotaId, Long propietarioId) {
        Mascota mascota = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una mascota con id " + mascotaId));

        if (!mascota.getPropietario().getId().equals(propietarioId)) {
            throw new RuntimeException("La mascota no pertenece al usuario autenticado");
        }

        bano.setMascota(mascota);
        return banoRepository.save(bano);
    }

    @Override
    public Bano obtenerPorId(Long id) {
        return banoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un baño con id " + id));
    }

    @Override
    public Bano obtenerPorIdYPropietario(Long id, Long propietarioId) {
        return banoRepository.findByIdAndPropietarioId(id, propietarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un baño con id " + id + " para este usuario"));
    }

    @Override
    public List<Bano> listarPorPropietario(Long propietarioId) {
        return banoRepository.findByPropietarioId(propietarioId);
    }

    @Override
    public Bano actualizar(Long id, Bano datosActualizados, Long propietarioId) {
        Bano existente = obtenerPorIdYPropietario(id, propietarioId);

        existente.setFecha(datosActualizados.getFecha());
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

        return banoRepository.save(existente);
    }

    @Override
    public void eliminar(Long id, Long propietarioId) {
        Bano existente = obtenerPorIdYPropietario(id, propietarioId);
        banoRepository.delete(existente);
    }
}