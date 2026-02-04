package com.example.apuntes.repository;

import com.example.apuntes.model.Apunte;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ApunteRepository extends JpaRepository<Apunte, Long> {

    List<Apunte> findByTituloContainingIgnoreCase(String titulo);
    List<Apunte> findByAsignaturaId(Long asignaturaId);
    List<Apunte> findByTituloContainingIgnoreCaseAndAsignaturaId(String titulo, Long asignaturaId);
}
