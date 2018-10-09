package models.accounts;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class AccountCollectionTest {

    @Test
    public void returnTrueWhenAccountsListIsNull() {

        AccountCollection accountCollection = new AccountCollection(Collections.emptyList());

        assertThat(accountCollection.isEmpty(), is(true));
    }

    @Test
    public void returnTrueWhenAccountsListIsEmpty() {

        AccountCollection accountCollection = new AccountCollection(Collections.emptyList());

        assertThat(accountCollection.isEmpty(), is(true));
    }

    @Test
    public void returnFalseWhenAccountsListHasElements() {

        List<Account> accounts = mock(List.class);

        AccountCollection accountCollection = new AccountCollection(accounts);

        assertThat(accountCollection.isEmpty(), is(false));
    }

    @Test
    public void returnFirstAccountAsSingleResult() {

        Account account1 = mock(Account.class);
        Account account2 = mock(Account.class);

        List<Account> accounts = Arrays.asList(account1, account2);
        AccountCollection bankCollection = new AccountCollection(accounts);

        assertThat(bankCollection.singleResult(), is(account1));
    }
}