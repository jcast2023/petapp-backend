package com.petapp.backend.repository;

import com.petapp.backend.model.Vacuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VacunaRepository extends JpaRepository<Vacuna, Long> {

    @Query("SELECT v FROM Vacuna v JOIN FETCH v.mascota WHERE v.mascota.propietario.id = :propietarioId")
    List<Vacuna> findByPropietarioId(@Param("propietarioId") Long propietarioId);

    @Query("SELECT v FROM Vacuna v JOIN FETCH v.mascota WHERE v.id = :id AND v.mascota.propietario.id = :propietarioId")
    Optional<Vacuna> findByIdAndPropietarioId(@Param("id") Long id, @Param("propietarioId") Long propietarioId);
}