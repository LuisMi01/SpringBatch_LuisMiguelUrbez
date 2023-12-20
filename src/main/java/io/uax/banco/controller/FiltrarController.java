package io.uax.banco.controller;

import io.uax.banco.domain.Usuario;
import io.uax.banco.repos.UsuarioRepository;
import io.uax.banco.trasks.FiltrarTask;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; import java.util.concurrent.ForkJoinPool;


import java.util.List;

@Controller
@RequestMapping("/filtrar")
public class FiltrarController {

    private final UsuarioRepository usuarioRepository;

    public FiltrarController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @GetMapping
    public String filter(@RequestParam("minAmount") Double minAmount,
                         @RequestParam("maxAmount") Double maxAmount, Model model) {
        List<Usuario> usuarios = usuarioRepository.findByAmountBetween(minAmount, maxAmount);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        FiltrarTask filtrarTask = new FiltrarTask(usuarios);
        List<Usuario> sortedUsuarios = forkJoinPool.invoke(filtrarTask);

        model.addAttribute("usuarios", sortedUsuarios);
        return "filterResult";
    }
}