package com.petapp.backend.repository;

import com.petapp.backend.model.Bano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BanoRepository extends JpaRepository<Bano, Long> {

    @Query("SELECT b FROM Bano b JOIN FETCH b.mascota WHERE b.mascota.propietario.id = :propietarioId")
    List<Bano> findByPropietarioId(@Param("propietarioId") Long propietarioId);

    @Query("SELECT b FROM Bano b JOIN FETCH b.mascota WHERE b.id = :id AND b.mascota.propietario.id = :propietarioId")
    Optional<Bano> findByIdAndPropietarioId(@Param("id") Long id, @Param("propietarioId") Long propietarioId);
}