package io.uax.banco.controller;

import io.uax.banco.domain.Usuario;
import io.uax.banco.repos.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/filtrar")
public class FiltrarController {

    private final UsuarioRepository usuarioRepository;

    public FiltrarController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String search(@RequestParam(value = "accountId", required = false) String accountId, Model model) {
        if (accountId != null) {
            Optional<Usuario> usuario = usuarioRepository.findByAccountId(accountId);
            usuario.ifPresent(value -> model.addAttribute("usuario", value));
        }
        return "filtrar/filtro";
    }
}
/*
    @GetMapping
       public String filter(@RequestParam(value = "minAmount", required = false) Double minAmount,
                         @RequestParam(value = "maxAmount", required = false) Double maxAmount, Model model) {
        if (minAmount != null && maxAmount != null) {
            List<Usuario> usuarios = usuarioRepository.findByAmountBetween(minAmount, maxAmount);

            ForkJoinPool forkJoinPool = new ForkJoinPool();
            FiltrarTask filtrarTask = new FiltrarTask(usuarios);
            List<Usuario> sortedUsuarios = forkJoinPool.invoke(filtrarTask);

            model.addAttribute("usuarios", sortedUsuarios);
        }
        return "filtrar/filtro";
    }*/
