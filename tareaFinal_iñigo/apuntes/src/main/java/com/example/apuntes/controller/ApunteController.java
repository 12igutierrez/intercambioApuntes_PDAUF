package com.example.apuntes.controller;

import com.example.apuntes.model.*;
import com.example.apuntes.repository.ApunteRepository;
import com.example.apuntes.repository.ComentarioRepository;
import com.example.apuntes.repository.UsuarioRepository;
import com.example.apuntes.repository.AsignaturaRepository;
import com.example.apuntes.repository.ValoracionRepository;
import com.example.apuntes.security.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/apuntes")
public class ApunteController {

    private final ApunteRepository apunteRepository;
    private final ComentarioRepository comentarioRepository;
    private final ValoracionRepository valoracionRepository;
    private final UsuarioRepository usuarioRepository;
    private final AsignaturaRepository asignaturaRepository;

    public ApunteController(
            ApunteRepository apunteRepository,
            ComentarioRepository comentarioRepository,
            ValoracionRepository valoracionRepository,
            UsuarioRepository usuarioRepository,
            AsignaturaRepository asignaturaRepository) {

        this.apunteRepository = apunteRepository;
        this.comentarioRepository = comentarioRepository;
        this.valoracionRepository = valoracionRepository;
        this.usuarioRepository = usuarioRepository;
        this.asignaturaRepository = asignaturaRepository;
    }

    @InitBinder("nuevoComentario")
    public void initBinderComentario(WebDataBinder binder) {
        binder.setDisallowedFields("id");
    }

    @GetMapping("/{id}")
    public String detalleApunte(@PathVariable Long id, Model model) {

        Apunte apunte = apunteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Apunte no encontrado"));

        model.addAttribute("apunte", apunte);
        model.addAttribute("comentarios", comentarioRepository.findByApunteId(id));
        model.addAttribute("valoraciones", valoracionRepository.findByApunteId(id));

        model.addAttribute("nuevoComentario", new Comentario());
        model.addAttribute("nuevaValoracion", new Valoracion());

        // ===== BLOQUE C =====
        String username = SecurityUtils.getUsernameActual();

        if (username != null) {
            usuarioRepository.findByUsername(username).ifPresent(usuario -> {
                valoracionRepository
                        .findByApunteIdAndUsuarioId(apunte.getId(), usuario.getId())
                        .ifPresent(valoracion ->
                                model.addAttribute("valoracionUsuario", valoracion)
                        );
            });
        }

        // ===== BLOQUE D: MEDIA DE VALORACIONES =====
        Double mediaValoraciones =
                valoracionRepository.calcularMediaPorApunte(apunte.getId());

        if (mediaValoraciones != null) {
            model.addAttribute("mediaValoraciones",
                    Math.round(mediaValoraciones * 10.0) / 10.0);
        }

        return "apunte-detalle";
    }


    @PostMapping("/{id}/comentario")
    public String guardarComentario(
            @PathVariable Long id,
            @ModelAttribute("nuevoComentario") Comentario comentario) {

        String username = SecurityUtils.getUsernameActual();
        if (username == null) {
            return "redirect:/login";
        }

        Apunte apunte = apunteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Apunte no encontrado"));

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        comentario.setApunte(apunte);
        comentario.setUsuario(usuario);

        comentarioRepository.save(comentario);

        return "redirect:/apuntes/" + id;
    }

    @PostMapping("/{id}/valoracion")
    public String guardarValoracion(
            @PathVariable Long id,
            @ModelAttribute("nuevaValoracion") Valoracion valoracion) {

        String username = SecurityUtils.getUsernameActual();
        if (username == null) {
            return "redirect:/login";
        }

        Apunte apunte = apunteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Apunte no encontrado"));

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        valoracion.setApunte(apunte);
        valoracion.setUsuario(usuario);

        valoracionRepository.save(valoracion);

        return "redirect:/apuntes/" + id;
    }

    @GetMapping("/nuevo")
    public String nuevoApunteForm(Model model) {

        String username = SecurityUtils.getUsernameActual();
        if (username == null) {
            return "redirect:/login";
        }

        model.addAttribute("apunte", new Apunte());

        model.addAttribute("asignaturas", asignaturaRepository.findAll());

        return "apunte-form";
    }


    @PostMapping("/nuevo")
    public String guardarApunte(
            @ModelAttribute Apunte apunte,
            @RequestParam("asignatura") Long asignaturaId) {

        String username = SecurityUtils.getUsernameActual();
        if (username == null) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Asignatura asignatura = asignaturaRepository.findById(asignaturaId)
                .orElseThrow(() -> new IllegalArgumentException("Asignatura no encontrada"));

        apunte.setUsuario(usuario);
        apunte.setAsignatura(asignatura);

        apunteRepository.save(apunte);

        return "redirect:/";
    }

    @InitBinder("apunte")
    public void initBinderApunte(WebDataBinder binder) {
        binder.setDisallowedFields(
                "id",
                "usuario",
                "comentarios",
                "valoraciones"
        );
    }


    @GetMapping("/{id}/editar")
    public String editarApunteForm(@PathVariable Long id, Model model) {

        String username = SecurityUtils.getUsernameActual();
        if (username == null) {
            return "redirect:/login";
        }

        Apunte apunte = apunteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Apunte no encontrado"));

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        boolean esAdmin = usuario.getRol().equals("ROLE_ADMIN");
        boolean esPropietario = apunte.getUsuario().getId().equals(usuario.getId());

        if (!esAdmin && !esPropietario) {
            return "redirect:/";
        }

        model.addAttribute("apunte", apunte);
        model.addAttribute("asignaturas", asignaturaRepository.findAll());

        return "apunte-form";
    }


    @PostMapping("/{id}/editar")
    public String actualizarApunte(
            @PathVariable Long id,
            @ModelAttribute Apunte apunteForm) {

        String username = SecurityUtils.getUsernameActual();
        if (username == null) {
            return "redirect:/login";
        }

        Apunte apunte = apunteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Apunte no encontrado"));

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        boolean esAdmin = usuario.getRol().equals("ROLE_ADMIN");
        boolean esPropietario = apunte.getUsuario().getId().equals(usuario.getId());

        if (!esAdmin && !esPropietario) {
            return "redirect:/";
        }

        apunte.setTitulo(apunteForm.getTitulo());
        apunte.setDescripcion(apunteForm.getDescripcion());
        apunte.setAsignatura(apunteForm.getAsignatura());

        apunteRepository.save(apunte);

        return "redirect:/apuntes/" + id;
    }


    @PostMapping("/{id}/eliminar")
    public String eliminarApunte(@PathVariable Long id) {

        String username = SecurityUtils.getUsernameActual();
        if (username == null) {
            return "redirect:/login";
        }

        Apunte apunte = apunteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Apunte no encontrado"));

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        boolean esAdmin = usuario.getRol().equals("ROLE_ADMIN");
        boolean esPropietario = apunte.getUsuario().getId().equals(usuario.getId());

        if (!esAdmin && !esPropietario) {
            return "redirect:/";
        }

        apunteRepository.delete(apunte);

        return "redirect:/";
    }

}

