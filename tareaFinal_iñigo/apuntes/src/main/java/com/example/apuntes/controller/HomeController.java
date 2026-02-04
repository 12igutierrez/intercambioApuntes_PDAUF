package com.example.apuntes.controller;

import com.example.apuntes.model.Apunte;
import com.example.apuntes.repository.ApunteRepository;
import com.example.apuntes.repository.AsignaturaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final ApunteRepository apunteRepository;
    private final AsignaturaRepository asignaturaRepository;

    public HomeController(ApunteRepository apunteRepository, AsignaturaRepository asignaturaRepository) {
        this.apunteRepository = apunteRepository;
        this.asignaturaRepository = asignaturaRepository;
    }

    @GetMapping("/")
    public String home(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Long asignaturaId,
            Model model) {

        List<Apunte> apuntes;

        if (q != null && !q.isBlank() && asignaturaId != null) {
            apuntes = apunteRepository.findByTituloContainingIgnoreCaseAndAsignaturaId(q, asignaturaId);
        } else if (q != null && !q.isBlank()) {
            apuntes = apunteRepository.findByTituloContainingIgnoreCase(q);
        } else if (asignaturaId != null) {
            apuntes = apunteRepository.findByAsignaturaId(asignaturaId);
        } else {
            apuntes = apunteRepository.findAll();
        }

        model.addAttribute("apuntes", apuntes);
        model.addAttribute("q", q);
        model.addAttribute("asignaturaId", asignaturaId);
        model.addAttribute("asignaturas", asignaturaRepository.findAll());

        return "index";
    }


}

