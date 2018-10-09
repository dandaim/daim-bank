package models.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import models.branches.Branch;
import models.customers.Customer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "account_owner_id")
    private Customer customer;

    @Column(name = "account_status")
    private Boolean accountStatus;

    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "current_balance")
    private Double currentBalance;

    public Account() {}

    public Account(Branch branch, Customer customer, Boolean accountStatus, AccountType accountType, Double currentBalance) {
        this.branch = branch;
        this.customer = customer;
        this.accountStatus = accountStatus;
        this.accountType = accountType;
        this.currentBalance = currentBalance;
    }

    public Account(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    @JsonProperty
    public Long id() {
        return id;
    }

    @JsonProperty
    public Long customerId() {
        return customer.id();
    }

    @JsonProperty
    public String accountStatus() {
        return accountStatus ? "active" : "inactive";
    }

    @JsonProperty
    public AccountType accountType() {
        return accountType;
    }

    @JsonProperty
    public Double currentBalance() {
        return currentBalance;
    }

    public void debitBalance(Double amount) {
        this.currentBalance -= amount;
    }

    public void addBalance(Double amount) {
        this.currentBalance += amount;
    }
}
