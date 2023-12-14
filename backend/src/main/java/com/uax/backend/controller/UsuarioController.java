package com.uax.backend.controller;

import com.uax.backend.model.Transaction;
import com.uax.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final TransactionService transactionService;

    @Autowired
    public UsuarioController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transaction> getAllUsuarios() {
        return transactionService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public Transaction getUsuarioById(@PathVariable String id) {
        return transactionService.getUsuarioById(id);
    }

    @PostMapping
    public Transaction saveUsuario(@RequestBody Transaction usuario) {
        return transactionService.saveUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable String id) {
        transactionService.deleteUsuario(id);
    }
}