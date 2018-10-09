package models.customers;

import com.fasterxml.jackson.annotation.JsonProperty;
import models.branches.Branch;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Branch branch;

    @Column(name = "name")
    private String name;

    @Column(name = "contact_details")
    private String contactDetails;

    @Column(name = "email")
    private String email;

    public Customer() {}

    public Customer(Branch branch, String name, String contactDetails, String email) {
        this.branch = branch;
        this.name = name;
        this.contactDetails = contactDetails;
        this.email = email;
    }

    @JsonProperty
    public Long id() {
        return id;
    }

    @JsonProperty
    public Long branchId() {
        return branch.id();
    }

    @JsonProperty
    public String name() {
        return name;
    }

    @JsonProperty
    public String contactDetails() {
        return contactDetails;
    }

    @JsonProperty
    public String email() {
        return email;
    }
}
