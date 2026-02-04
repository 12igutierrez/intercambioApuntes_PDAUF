package com.example.apuntes.repository;

import com.example.apuntes.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByApunteId(Long apunteId);
}
