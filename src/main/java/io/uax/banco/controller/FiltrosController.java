package io.uax.banco.controller;
import io.uax.banco.domain.Usuario;
import io.uax.banco.repos.UsuarioRepository;
import io.uax.banco.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/filtros")
public class FiltrosController {

    private final UsuarioService usuarioService;

    public FiltrosController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "filtros/filtro";
    }
}