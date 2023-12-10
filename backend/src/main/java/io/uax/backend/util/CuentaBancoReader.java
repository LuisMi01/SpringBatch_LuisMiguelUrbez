package io.uax.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.uax.backend.model.CuentaBanco;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class CuentaBancoReader implements Converter<String, CuentaBanco> {

    private final ObjectMapper objectMapper;

    public CuentaBancoReader(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    @SneakyThrows
    public CuentaBanco convert(final String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        return objectMapper.readValue(value, CuentaBanco.class);
    }

}
