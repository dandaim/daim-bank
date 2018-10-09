package models.banks;

import java.util.List;

public class BankCollection {

    private List<Bank> banks;

    public BankCollection(List<Bank> banks) {
        this.banks = banks;
    }

    public List<Bank> data() {
        return this.banks;
    }

    public Bank singleResult() {
        return banks.get(0);
    }

    public boolean isNotEmpty() {
        return !banks.isEmpty();
    }
}
