package io.uax.banco.processor;

import io.uax.banco.domain.Usuario;
import org.springframework.batch.item.ItemProcessor;

public class UsuarioProcessor implements ItemProcessor<Usuario,Usuario> {
    @Override
    public Usuario process(Usuario usuario) throws Exception {
        // Validar accountId
        if (usuario.getAccountId() == null || usuario.getAccountId().isEmpty()) {
            throw new Exception("AccountId no puede estar vacío");
        }

        // Validar transactionType
        if (usuario.getTransactionType() == null) {
            throw new Exception("TransactionType no puede ser nulo");
        }

        // Validar amount
        if (usuario.getAmount() == null || usuario.getAmount() < 0) {
            throw new Exception("Amount no puede ser nulo o negativo");
        }

        // Validar transactionDate
        if (usuario.getTransactionDate() == null) {
            throw new Exception("TransactionDate no puede ser nulo");
        }

        // Si todas las validaciones pasan, devolver el usuario
        return usuario;
    }
}
