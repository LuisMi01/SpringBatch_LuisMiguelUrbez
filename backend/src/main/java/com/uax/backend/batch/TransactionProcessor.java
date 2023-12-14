package com.uax.backend.batch;

import com.uax.backend.model.Transaction;
import org.springframework.batch.item.ItemProcessor;

public class TransactionProcessor implements ItemProcessor<Transaction, Transaction> {
    @Override
    public Transaction process(Transaction transaction) {
        // Lógica de procesamiento...
        return transaction;
    }
}
