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

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            UsuarioRepository usuarioRepository,
            AsignaturaRepository asignaturaRepository,
            ApunteRepository apunteRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            /* =======================
               USUARIOS
               ======================= */

            Usuario admin = usuarioRepository.findByUsername("admin")
                    .orElseGet(() -> {
                        Usuario u = new Usuario();
                        u.setUsername("admin");
                        u.setPassword(passwordEncoder.encode("admin123"));
                        u.setRol("ADMIN");
                        return usuarioRepository.save(u);
                    });

            Usuario usuario1 = usuarioRepository.findByUsername("usuario1")
                    .orElseGet(() -> {
                        Usuario u = new Usuario();
                        u.setUsername("usuario1");
                        u.setPassword(passwordEncoder.encode("usuario123"));
                        u.setRol("USER");
                        return usuarioRepository.save(u);
                    });

            Usuario usuario2 = usuarioRepository.findByUsername("usuario2")
                    .orElseGet(() -> {
                        Usuario u = new Usuario();
                        u.setUsername("usuario2");
                        u.setPassword(passwordEncoder.encode("usuario123"));
                        u.setRol("USER");
                        return usuarioRepository.save(u);
                    });

            /* =======================
               ASIGNATURAS
               ======================= */

            List<String> nombresAsignaturas = List.of(
                    "Acceso a datos",
                    "Desarrollo de interfaces",
                    "Programación de aplicaciones frameworks",
                    "Programación de servicios y procesos",
                    "Programación multimedia y dispositivos móviles",
                    "Proyecto intermodular",
                    "Sistemas de gestión empresarial"
            );

            for (String nombre : nombresAsignaturas) {
                asignaturaRepository.findByNombre(nombre)
                        .orElseGet(() -> {
                            Asignatura a = new Asignatura();
                            a.setNombre(nombre);
                            return asignaturaRepository.save(a);
                        });
            }

            if (apunteRepository.count() > 0) {
                return; // Ya hay apuntes → no duplicamos
            }

            /* =======================
               APUNTES DE EJEMPLO
               ======================= */

            crearApunte(
                    "UD1 Manejo de ficheros",
                    "En este capítulo aprenderemos a leer y escribir datos en ficheros secuenciales y aleatorios en Java. Utilizaremos diferentes tipos de ficheros de intercambio de datos (XML, JSON, CSV). Aprenderemos a serializar objetos JAVA a distintos formatos y a utilizar y gestionar excepciones",
                    "Acceso a datos",
                    usuario1,
                    asignaturaRepository,
                    apunteRepository
            );

            crearApunte(
                    "Información de la Tarea 3",
                    "El SGDB que recomiendo utilizar es MySQL, también podéis utilizar otros como MariaDB, con lo que decidáis lo indicáis en el README la tecnología que emplea. Al hacer doble clic en el ejecutable se deberá crear el esquema de base de datos que hayáis definido automáticamente en el SGDB en caso de no estar creado en esa máquina y no de errores.",
                    "Desarrollo de interfaces",
                    usuario2,
                    asignaturaRepository,
                    apunteRepository
            );

            crearApunte(
                    "Tema 7 ficheros",
                    "La gestión de subida y almacenamiento de ficheros es una funcionalidad esencial en muchas aplicaciones web modernas, permitiendo que los usuarios adjunten documentos, imágenes u otros recursos directamente desde el navegador.",
                    "Programación de aplicaciones frameworks",
                    usuario1,
                    asignaturaRepository,
                    apunteRepository
            );

            crearApunte(
                    "UD4 Generación de servicios en red",
                    "Una red de ordenadores o red informática la podemos definir como un sistema de comunicaciones que conecta ordenadores y otros equipos informáticos entre sí, con la finalidad de compartir información y recursos.",
                    "Programación de servicios y procesos",
                    usuario2,
                    asignaturaRepository,
                    apunteRepository
            );

            crearApunte(
                    "Introducción a Android Studio",
                    "Android Studio es el entorno de desarrollo oficial para Android. Está basado en IntelliJ IDEA e incluye herramientas para diseñar interfaces, escribir código, compilar, ejecutar y depurar aplicaciones. Un proyecto contiene código Kotlin, recursos y configuración en Gradle.",
                    "Programación multimedia y dispositivos móviles",
                    usuario1,
                    asignaturaRepository,
                    apunteRepository
            );

            crearApunte(
                    "Instrucciones iniciales Odoo",
                    "Debe incluir: Nombre y sector de actividad, Ubicación, Tamaño, Modelo de negocio, Misión y Principales productos o servicios.",
                    "Sistemas de gestión empresarial",
                    usuario1,
                    asignaturaRepository,
                    apunteRepository
            );
        };
    }

    private void crearApunte(
            String titulo,
            String descripcion,
            String nombreAsignatura,
            Usuario usuario,
            AsignaturaRepository asignaturaRepository,
            ApunteRepository apunteRepository
    ) {
        Asignatura asignatura = asignaturaRepository.findByNombre(nombreAsignatura).orElseThrow();

        Apunte apunte = new Apunte();
        apunte.setTitulo(titulo);
        apunte.setDescripcion(descripcion);
        apunte.setUsuario(usuario);
        apunte.setAsignatura(asignatura);

        apunteRepository.save(apunte);
    }
}
