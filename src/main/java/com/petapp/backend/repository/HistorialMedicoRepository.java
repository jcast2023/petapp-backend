package com.petapp.backend.repository;

import com.petapp.backend.model.HistorialMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico, Long> {

    @Query("SELECT h FROM HistorialMedico h JOIN FETCH h.mascota WHERE h.mascota.propietario.id = :propietarioId ORDER BY h.fecha DESC")
    List<HistorialMedico> findByPropietarioId(@Param("propietarioId") Long propietarioId);

    @Query("SELECT h FROM HistorialMedico h JOIN FETCH h.mascota WHERE h.id = :id AND h.mascota.propietario.id = :propietarioId")
    Optional<HistorialMedico> findByIdAndPropietarioId(@Param("id") Long id, @Param("propietarioId") Long propietarioId);
}