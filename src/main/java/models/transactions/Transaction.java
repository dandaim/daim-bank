package models.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import models.accounts.Account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_from_id")
    private Account accountFrom;

    @ManyToOne
    @JoinColumn(name = "account_to_id")
    private Account accountTo;

    @Column(name = "transaction_amount")
    private Double amount;

    @Column(name = "transaction_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    public Transaction() {}

    public Transaction(Account accountFrom, Account accountTo, Double amount, Date transactionDate, TransactionType transactionType) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
    }

    public Transaction(Account accountFrom, Account accountTo, Double amount, TransactionType type) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.transactionType = type;
        this.transactionDate = new Date();
    }

    public boolean hasInsufficientFunds() {
        return accountFrom.currentBalance() < amount;
    }

    @JsonProperty
    public Double amount() {
        return amount;
    }

    @JsonProperty
    public Long getId() {
        return id;
    }

    @JsonProperty
    public Long accountFromId() {
        return accountFrom.id();
    }

    @JsonProperty
    public Long accountToId() {
        return accountTo.id();
    }

    @JsonProperty
    public Date transactionDate() {
        return transactionDate;
    }

    @JsonProperty
    public TransactionType transactionType() {
        return transactionType;
    }
}
