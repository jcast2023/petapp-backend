package com.petapp.backend.serviceImpl;

import com.petapp.backend.dto.DesparasitacionRequestDTO;
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
    public Desparasitacion crear(DesparasitacionRequestDTO request, Long mascotaId, Long propietarioId) {
        Mascota mascota = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una mascota con id " + mascotaId));

        if (!mascota.getPropietario().getId().equals(propietarioId)) {
            throw new RuntimeException("La mascota no pertenece al usuario autenticado");
        }

        Desparasitacion desparasitacion = new Desparasitacion();
        desparasitacion.setTipo(request.getTipo());
        desparasitacion.setProducto(request.getProducto());
        desparasitacion.setFechaAplicacion(request.getFechaAplicacion());
        desparasitacion.setFechaProximaDosis(request.getFechaProximaDosis());
        desparasitacion.setNotas(request.getNotas());
        desparasitacion.setMascota(mascota);
        return desparasitacionRepository.save(desparasitacion);
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
        return desparasitacionRepository.findByPropietarioId(propietarioId);
    }

    @Override
    @Transactional
    public Desparasitacion actualizar(Long id, DesparasitacionRequestDTO request, Long propietarioId) {
        Desparasitacion existente = obtenerPorIdYPropietario(id, propietarioId);

        existente.setTipo(request.getTipo());
        existente.setProducto(request.getProducto());
        existente.setFechaAplicacion(request.getFechaAplicacion());
        existente.setFechaProximaDosis(request.getFechaProximaDosis());
        existente.setNotas(request.getNotas());
        return desparasitacionRepository.save(existente);
    }

    @Override
    public void eliminar(Long id, Long propietarioId) {
        Desparasitacion existente = obtenerPorIdYPropietario(id, propietarioId);
        desparasitacionRepository.delete(existente);
    }
}