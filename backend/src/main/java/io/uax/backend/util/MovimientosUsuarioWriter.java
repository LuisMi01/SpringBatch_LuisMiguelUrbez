package io.uax.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.uax.backend.model.MovimientosUsuario;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class MovimientosUsuarioWriter implements Converter<MovimientosUsuario, String> {

    private final ObjectMapper objectMapper;

    public MovimientosUsuarioWriter(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    @SneakyThrows
    public String convert(final MovimientosUsuario value) {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
    }

}
