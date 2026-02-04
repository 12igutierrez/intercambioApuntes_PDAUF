package com.example.apuntes.repository;

import com.example.apuntes.model.MensajeContacto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensajeContactoRepository
        extends JpaRepository<MensajeContacto, Long> {
}
