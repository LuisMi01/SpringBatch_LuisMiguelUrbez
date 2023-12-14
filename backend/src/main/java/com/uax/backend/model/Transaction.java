package com.uax.backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "banco")
public class Transaction {
    @Id
    private String id;
    private String accountId;
    private String transactionType;
    private double amount;
    private Date transactionDate;
}
