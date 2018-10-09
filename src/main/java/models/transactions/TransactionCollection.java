package models.transactions;

import java.util.List;

public class TransactionCollection {

    private List<Transaction> transactions;

    public TransactionCollection(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> data() {
        return transactions;
    }
}
