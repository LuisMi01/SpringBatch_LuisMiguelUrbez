package com.uax.backend.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "banco")
public class Usuario {
    @Id
    private String id;
    private String nombre;
    private String email;
    private String password;
    private Double saldo;
    private String cuenta;
    private List<Double> movimientos;
}
