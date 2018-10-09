package models.banks;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public Bank() {
    }

    public Bank(String name) {
        this.name = name;
    }

    @JsonProperty
    public Long id() {
        return id;
    }

    @JsonProperty
    public String name() {
        return name;
    }
}
