package models.branches;

import com.fasterxml.jackson.annotation.JsonProperty;
import models.banks.Bank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Bank bank;

    @Column(name = "branch_details")
    private String branchDetails;

    public Branch() {

    }

    public Branch(Bank bank, String branchDetails) {
        this.bank = bank;
        this.branchDetails = branchDetails;
    }

    @JsonProperty
    public Long id() {
        return id;
    }

    @JsonProperty
    public Long bankId() {
        return bank.id();
    }

    @JsonProperty
    public String branchDetails() {
        return branchDetails;
    }
}
