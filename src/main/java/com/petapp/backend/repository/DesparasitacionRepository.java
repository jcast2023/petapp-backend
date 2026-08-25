package com.petapp.backend.repository;

import com.petapp.backend.model.Desparasitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DesparasitacionRepository extends JpaRepository<Desparasitacion, Long> {

    @Query("SELECT d FROM Desparasitacion d JOIN FETCH d.mascota WHERE d.mascota.propietario.id = :propietarioId")
    List<Desparasitacion> findByPropietarioId(@Param("propietarioId") Long propietarioId);

    @Query("SELECT d FROM Desparasitacion d JOIN FETCH d.mascota WHERE d.id = :id AND d.mascota.propietario.id = :propietarioId")
    Optional<Desparasitacion> findByIdAndPropietarioId(@Param("id") Long id, @Param("propietarioId") Long propietarioId);
}