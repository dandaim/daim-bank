package models.customers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CustomerCollection {

    private final List<Customer> customers;

    public CustomerCollection(List<Customer> customers) {
        this.customers = customers;
    }

    @JsonProperty
    public List<Customer> data() {
        return this.customers;
    }
}
