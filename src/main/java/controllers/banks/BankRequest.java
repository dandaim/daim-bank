package controllers.banks;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import models.banks.Bank;

import javax.validation.constraints.Size;

public class BankRequest {

    @Size(min = 5, max = 255)
    private String name;

    public BankRequest() {

    }

    @JsonCreator
    public BankRequest(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public Bank toBank() {
        return new Bank(name);
    }

}
