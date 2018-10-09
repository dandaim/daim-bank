package controllers.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionError {

    private String message;

    public TransactionError(String message) {
        this.message = message;
    }

    @JsonProperty
    public String message() {
        return message;
    }
}
