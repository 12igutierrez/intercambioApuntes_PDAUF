package com.example.apuntes.controller;

import com.example.apuntes.model.Usuario;
import com.example.apuntes.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registro")
    public String registroForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(
            @ModelAttribute Usuario usuario,
            Model model) {

        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            model.addAttribute("error", "El usuario ya existe");
            return "registro";
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol("USER");

        usuarioRepository.save(usuario);

        return "redirect:/login";
    }

}
