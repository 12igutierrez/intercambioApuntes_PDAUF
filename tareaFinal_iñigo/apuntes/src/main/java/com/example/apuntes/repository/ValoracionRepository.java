package com.example.apuntes.repository;

import com.example.apuntes.model.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {
    List<Valoracion> findByApunteId(Long apunteId);
    Optional<Valoracion> findByApunteIdAndUsuarioId(Long apunteId, Long usuarioId);

    @Query("SELECT AVG(v.puntuacion) FROM Valoracion v WHERE v.apunte.id = :apunteId")
    Double calcularMediaPorApunte(@Param("apunteId") Long apunteId);
}
