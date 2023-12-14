package com.uax.backend.batch;

import com.uax.backend.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

public class UsuarioItemProcessor implements ItemProcessor<Usuario, Usuario> {
    private static final Logger log = LoggerFactory.getLogger(UsuarioItemProcessor.class);

    @Override
    public Usuario process(final Usuario usuario)
            throws Exception {
        final String id = usuario.getId().toUpperCase();
        final String nombre = usuario.getNombre().toUpperCase();
        final String email = usuario.getEmail().toUpperCase();
        final String password = usuario.getPassword().toUpperCase();
        final Double saldo = usuario.getSaldo();
        final String cuenta = usuario.getCuenta().toUpperCase();
        final List<Double> movimientos = usuario.getMovimientos();
        final Usuario transformedComunidad = new
                Usuario(id, nombre, email, password, saldo, cuenta, movimientos);
        log.info("Converting (" + usuario + ") into (" + transformedComunidad + ")");

        return transformedComunidad;
    }
}
