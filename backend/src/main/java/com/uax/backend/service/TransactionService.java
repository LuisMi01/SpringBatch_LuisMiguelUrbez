package com.uax.backend.service;


import com.uax.backend.model.Transaction;
import com.uax.backend.repos.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllUsuarios() {
        return transactionRepository.findAll();
    }

    public Transaction getUsuarioById(String id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public Transaction saveUsuario(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void deleteUsuario(String id) {
        transactionRepository.deleteById(id);
    }
}
