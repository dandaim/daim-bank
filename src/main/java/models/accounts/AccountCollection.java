package models.accounts;

import java.util.List;
import java.util.Objects;

public class AccountCollection {

    private List<Account> accounts;

    public AccountCollection(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Account singleResult() {
        return this.accounts.get(0);
    }

    public boolean isEmpty() {
        return Objects.isNull(accounts) || accounts.isEmpty();
    }

    public List<Account> data() {
        return this.accounts;
    }
}
