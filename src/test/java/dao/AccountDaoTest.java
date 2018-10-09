package dao;

import com.google.inject.Provider;
import models.accounts.Account;
import models.accounts.AccountCollection;
import models.banks.Bank;
import models.banks.BankCollection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountDaoTest {

    @Mock
    private Provider<EntityManager> entityManagerProvider;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Account> query;

    @Mock
    private Query updateQuery;

    @InjectMocks
    private AccountDao accountDao;


    @Before
    public void setUp() {
        when(entityManagerProvider.get()).thenReturn(entityManager);
    }

    @Test
    public void returnAccountCollectionFromDatabase() {
        List<Account> accounts = Collections.emptyList();

        when(entityManager.createQuery("SELECT a FROM Account a", Account.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(accounts);

        AccountCollection accountCollection = accountDao.getAccounts();

        assertThat(accountCollection.data(), is(accounts));
        verify(entityManagerProvider).get();
    }

    @Test
    public void returnAccountCollectionFromDatabaseWhenGettingSingleAccount() {
        List<Account> accounts = Collections.singletonList(mock(Account.class));
        Long accountId = 1L;

        when(entityManager.createQuery("SELECT a FROM Account a WHERE a.id = :accountId", Account.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(accounts);

        AccountCollection accountCollection = accountDao.getAccount(accountId);

        assertThat(accountCollection.data(), is(accounts));

        verify(query).setParameter("accountId", accountId);
        verify(entityManagerProvider).get();
    }

    @Test
    public void updateAccountInDatabase() {

        Account account = mock(Account.class);

        when(account.currentBalance()).thenReturn(10.00);
        when(account.id()).thenReturn(1L);
        when(entityManager.createQuery("UPDATE Account SET currentBalance = :currentBalance WHERE id = :id")).thenReturn(updateQuery);

        accountDao.update(account);

        verify(updateQuery).setParameter("currentBalance", account.currentBalance());
        verify(updateQuery).setParameter("id", account.id());
        verify(updateQuery).executeUpdate();
    }
}