package com.example.apuntes.config;

import com.example.apuntes.model.Apunte;
import com.example.apuntes.model.Asignatura;
import com.example.apuntes.model.Usuario;
import com.example.apuntes.repository.ApunteRepository;
import com.example.apuntes.repository.AsignaturaRepository;
import com.example.apuntes.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner cargarDatos(
            UsuarioRepository usuarioRepository,
            AsignaturaRepository asignaturaRepository,
            ApunteRepository apunteRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            Usuario alumno1 = usuarioRepository.findByUsername("alumno1")
                    .orElseGet(() -> {
                        Usuario u = new Usuario();
                        u.setUsername("alumno1");
                        u.setPassword(passwordEncoder.encode("1234"));
                        u.setRol("USER");
                        return usuarioRepository.save(u);
                    });

            Usuario alumno2 = usuarioRepository.findByUsername("alumno2")
                    .orElseGet(() -> {
                        Usuario u = new Usuario();
                        u.setUsername("alumno2");
                        u.setPassword(passwordEncoder.encode("1234"));
                        u.setRol("USER");
                        return usuarioRepository.save(u);
                    });

            Usuario profesor = usuarioRepository.findByUsername("profesor")
                    .orElseGet(() -> {
                        Usuario u = new Usuario();
                        u.setUsername("profesor");
                        u.setPassword(passwordEncoder.encode("1234"));
                        u.setRol("ADMIN");
                        return usuarioRepository.save(u);
                    });

            Asignatura programacion = asignaturaRepository.findByNombre("Programación")
                    .orElseGet(() -> {
                        Asignatura a = new Asignatura();
                        a.setNombre("Programación");
                        return asignaturaRepository.save(a);
                    });

            if (apunteRepository.count() == 0) {
                Apunte apunte = new Apunte();
                apunte.setTitulo("Introducción a Java");
                apunte.setDescripcion("Apuntes básicos sobre Java y POO");
                apunte.setUsuario(alumno1);
                apunte.setAsignatura(programacion);

                apunteRepository.save(apunte);
            }
        };
    }
}

