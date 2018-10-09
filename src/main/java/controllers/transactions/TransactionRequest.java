package controllers.transactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import models.transactions.TransactionType;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class TransactionRequest {

    @DecimalMin(value = "0.00")
    private Double amount;

    @NotNull
    private TransactionType transactionType;

    @NotNull
    private Long accountFromId;

    @NotNull
    private Long accountToId;

    public TransactionRequest() {

    }

    @JsonCreator
    public TransactionRequest(@JsonProperty("amount") Double amount,
                              @JsonProperty("transactionType") TransactionType transactionType,
                              @JsonProperty("accountFromId") Long accountFromId,
                              @JsonProperty("accountToId") Long accountToId) {

        this.amount = amount;
        this.transactionType = transactionType;
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
    }

    public Long accountFrom() {
        return this.accountFromId;
    }

    public Long accountTo() {
        return this.accountToId;
    }

    public Double amount() {
        return amount;
    }

    public TransactionType type() {
        return transactionType;
    }
}
