package controllers.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import models.transactions.Transaction;
import models.transactions.TransactionType;

public class TransactionResponse {

    private Long id;
    private Double amount;
    private String transactionDate;
    private Long accountFromId;
    private Long accountToId;
    private TransactionType transactionType;

    public TransactionResponse() {

    }

    public TransactionResponse(Transaction transaction) {
        this.id = transaction.id();
        this.amount = transaction.amount();
        this.transactionDate = transaction.transactionDate();
        this.transactionType = transaction.transactionType();
        this.accountFromId = transaction.accountFromId();
        this.accountToId = transaction.accountToId();
    }

    @JsonProperty
    public Long id() {
        return id;
    }

    @JsonProperty
    public Double amount() {
        return amount;
    }

    @JsonProperty
    public String transactionDate() {
        return transactionDate;
    }

    @JsonProperty
    public Long accountFromId() {
        return accountFromId;
    }

    @JsonProperty
    public Long accountToId() {
        return accountToId;
    }

    @JsonProperty
    public TransactionType transactionType() {
        return transactionType;
    }
}
