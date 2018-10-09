package models.accounts;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void debitBalanceValueFromGivenAmount() {

        Double currentBalance = 10.0;
        Double amount = 6.0;

        Account account = new Account(currentBalance);
        account.debitBalance(amount);

        assertThat(account.currentBalance(), is(4.0));
    }

    @Test
    public void addBalanceValueFromGivenAmount() {

        Double currentBalance = 10.0;
        Double amount = 6.0;

        Account account = new Account(currentBalance);
        account.addBalance(amount);

        assertThat(account.currentBalance(), is(16.0));
    }
}