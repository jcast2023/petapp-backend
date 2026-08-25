package com.petapp.backend.repository;

import com.petapp.backend.model.VideoObediencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoObedienciaRepository extends JpaRepository<VideoObediencia, Long> {
    List<VideoObediencia> findByNivel(String nivel);
    List<VideoObediencia> findByComando(String comando);
}