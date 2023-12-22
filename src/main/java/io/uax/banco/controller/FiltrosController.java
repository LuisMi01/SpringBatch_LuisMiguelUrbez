package io.uax.banco.controller;

import io.uax.banco.domain.Usuario;
import io.uax.banco.repos.UsuarioRepository;
import io.uax.banco.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/filtros")
public class FiltrosController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public FiltrosController(UsuarioService usuarioService,
                             UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "filtros/filtro";
    }

    @GetMapping
    public List<Usuario> filterUsers(@RequestParam String filterType) {
        switch (filterType) {
            case "gasto":
                return usuarioService.findByGastoDesc();
            case "ingreso":
                return usuarioService.findByIngresoDesc();
            case "fechaAntiguo":
                return usuarioService.findByFechaAsc();
            case "fechaReciente":
                return usuarioService.findByFechaDesc();
            case "cuentaAZ":
                return usuarioService.findByCuentaAsc();
            case "cuentaZA":
                return usuarioService.findByCuentaDesc();
            default:
                return usuarioRepository.findAll();
        }
    }
}
