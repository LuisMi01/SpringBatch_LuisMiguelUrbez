package com.uax.backend.batch;

import com.uax.backend.model.Transaction;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidationException;

public class TransactionProcessor implements ItemProcessor<Transaction, Transaction> {
    @Override
    public Transaction process(Transaction transaction) throws ValidationException {
        // Validar el ID de la cuenta
        if (transaction.getAccountId() == null || transaction.getAccountId().isEmpty()) {
            throw new ValidationException("El ID de la cuenta no puede ser nulo o vacío");
        }

        // Validar el tipo de transacción
        if (transaction.getTransactionType() == null || transaction.getTransactionType().isEmpty()) {
            throw new ValidationException("El tipo de transacción no puede ser nulo o vacío");
        }

        // Validar el monto de la transacción
        if (transaction.getAmount() <= 0) {
            throw new ValidationException("El monto de la transacción debe ser mayor que cero");
        }

        // Validar la fecha de la transacción
        if (transaction.getTransactionDate() == null) {
            throw new ValidationException("La fecha de la transacción no puede ser nula");
        }

        return transaction;
    }
}