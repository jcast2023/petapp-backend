package com.petapp.backend.repository;

import com.petapp.backend.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findByPropietarioId(Long propietarioId);
    Optional<Mascota> findByIdAndPropietarioId(Long id, Long propietarioId);
}