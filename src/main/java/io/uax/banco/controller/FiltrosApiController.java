package io.uax.banco.controller;


import io.uax.banco.domain.Usuario;
import io.uax.banco.repos.UsuarioRepository;
import io.uax.banco.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filtros")
public class FiltrosApiController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public FiltrosApiController(UsuarioService usuarioService,
                                UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<Usuario> filterUsers(@RequestParam(required = false) String filterType) {
        if (filterType == null) {
            return usuarioRepository.findAll();
        }
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