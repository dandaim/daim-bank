package dao;

import com.google.inject.Provider;
import models.banks.Bank;
import models.banks.BankCollection;
import models.transactions.Transaction;
import models.transactions.TransactionCollection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class TransactionDaoTest {

    @InjectMocks
    private TransactionDao transactionDao;

    @Mock
    Provider<EntityManager> entityManagerProvider;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Transaction> query;

    @Before
    public void setUp() {

        when(entityManagerProvider.get()).thenReturn(entityManager);
    }

    @Test
    public void addTransactionToDatabase() {

        Transaction transaction = mock(Transaction.class);

        transactionDao.addTransaction(transaction);

        verify(entityManagerProvider).get();
        verify(entityManager).persist(transaction);
    }

    @Test
    public void returnTransactionCollectionFromDatabase() {
        List<Transaction> transactions = Collections.emptyList();

        when(entityManager.createQuery("SELECT t FROM Transaction t", Transaction.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(transactions);

        TransactionCollection transactionCollection = transactionDao.getTransactions();

        assertThat(transactionCollection.data(), is(transactions));
        verify(entityManagerProvider).get();
    }
}