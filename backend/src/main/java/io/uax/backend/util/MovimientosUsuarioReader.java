package io.uax.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.uax.backend.model.MovimientosUsuario;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class MovimientosUsuarioReader implements Converter<String, MovimientosUsuario> {

    private final ObjectMapper objectMapper;

    public MovimientosUsuarioReader(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    @SneakyThrows
    public MovimientosUsuario convert(final String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        return objectMapper.readValue(value, MovimientosUsuario.class);
    }

}
