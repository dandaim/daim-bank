package models.transactions;

import models.accounts.Account;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionTest {

    @Test
    public void returnTrueWhenCurrentBalanceIsLowerThanGivenAmount() {

        Double balance = 10.0;
        Double amount = 11.0;

        Account accountFrom = mock(Account.class);
        Account accountTo = mock(Account.class);

        when(accountFrom.currentBalance()).thenReturn(balance);

        Transaction transaction = new Transaction(accountFrom, accountTo, amount, TransactionType.DEPOSIT);

        assertThat(transaction.hasInsufficientFunds(), is(true));
    }

    @Test
    public void returnFalseWhenCurrentBalanceIsGreaterThanGivenAmount() {

        Double balance = 11.0;
        Double amount = 10.0;

        Account accountFrom = mock(Account.class);
        Account accountTo = mock(Account.class);

        when(accountFrom.currentBalance()).thenReturn(balance);

        Transaction transaction = new Transaction(accountFrom, accountTo, amount, TransactionType.DEPOSIT);

        assertThat(transaction.hasInsufficientFunds(), is(false));
    }

    @Test
    public void returnFalseWhenCurrentBalanceIsEqualToGivenAmount() {

        Double balance = 10.0;
        Double amount = 10.0;

        Account accountFrom = mock(Account.class);
        Account accountTo = mock(Account.class);

        when(accountFrom.currentBalance()).thenReturn(balance);

        Transaction transaction = new Transaction(accountFrom, accountTo, amount, TransactionType.DEPOSIT);

        assertThat(transaction.hasInsufficientFunds(), is(false));
    }
}