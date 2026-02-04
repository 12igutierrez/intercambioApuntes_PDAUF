package com.example.apuntes.controller;

import com.example.apuntes.model.MensajeContacto;
import com.example.apuntes.repository.MensajeContactoRepository;
import com.example.apuntes.service.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ContactoController {

    private final MensajeContactoRepository mensajeContactoRepository;
    private final EmailService emailService;

    public ContactoController(
            MensajeContactoRepository mensajeContactoRepository,
            EmailService emailService) {

        this.mensajeContactoRepository = mensajeContactoRepository;
        this.emailService = emailService;
    }

    @GetMapping("/contacto")
    public String mostrarContacto() {
        return "contacto";
    }

    @PostMapping("/contacto")
    public String procesarContacto(
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String mensaje,
            Model model) {

        // 1️⃣ Guardar en BD
        MensajeContacto mc = new MensajeContacto();
        mc.setNombre(nombre);
        mc.setEmail(email);
        mc.setMensaje(mensaje);
        mensajeContactoRepository.save(mc);

        // Enviar email
        emailService.enviarMensajeContacto(nombre, email, mensaje);

        model.addAttribute("enviado", true);
        return "contacto";
    }
}

