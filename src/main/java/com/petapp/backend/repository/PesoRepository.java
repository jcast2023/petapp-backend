package com.petapp.backend.repository;

import com.petapp.backend.model.Peso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PesoRepository extends JpaRepository<Peso, Long> {

    @Query("SELECT p FROM Peso p JOIN FETCH p.mascota WHERE p.mascota.propietario.id = :propietarioId ORDER BY p.fecha DESC")
    List<Peso> findByPropietarioId(@Param("propietarioId") Long propietarioId);

    @Query("SELECT p FROM Peso p JOIN FETCH p.mascota WHERE p.id = :id AND p.mascota.propietario.id = :propietarioId")
    Optional<Peso> findByIdAndPropietarioId(@Param("id") Long id, @Param("propietarioId") Long propietarioId);
}