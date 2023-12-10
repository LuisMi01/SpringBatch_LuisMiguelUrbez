package io.uax.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.uax.backend.model.CuentaBanco;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class CuentaBancoWriter implements Converter<CuentaBanco, String> {

    private final ObjectMapper objectMapper;

    public CuentaBancoWriter(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    @SneakyThrows
    public String convert(final CuentaBanco value) {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
    }

}
