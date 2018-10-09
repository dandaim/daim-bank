package dao;

import com.google.inject.Provider;
import controllers.banks.BankRequest;
import models.banks.Bank;
import models.banks.BankCollection;
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
public class BankDaoTest {

    @Mock
    private Provider<EntityManager> entityManagerProvider;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Bank> query;

    @InjectMocks
    private BankDao bankDao;

    @Before
    public void setUp() {
        when(entityManagerProvider.get()).thenReturn(entityManager);
    }

    @Test
    public void returnBankCollectionFromDatabase() {
        List<Bank> banks = Collections.emptyList();

        when(entityManager.createQuery("SELECT b FROM Bank b", Bank.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(banks);

        BankCollection bankCollection = bankDao.banks();

        assertThat(bankCollection.data(), is(banks));
        verify(entityManagerProvider).get();

    }

    @Test
    public void returnBankCollectionFromDatabaseWhenGettingSingleBank() {
        List<Bank> banks = Collections.singletonList(mock(Bank.class));
        Long bankId = 1L;

        when(entityManager.createQuery("SELECT b FROM Bank b WHERE b.id = :bankId", Bank.class)).thenReturn(query);
        when(query.setParameter("bankId", bankId)).thenReturn(query);
        when(query.getResultList()).thenReturn(banks);

        BankCollection bankCollection = bankDao.getBank(bankId);

        assertThat(bankCollection.data(), is(banks));

        verify(query).setParameter("bankId", bankId);
        verify(entityManagerProvider).get();

    }

    @Test
    public void addBankToDatabase() {

        BankRequest bankRequest = mock(BankRequest.class);
        Bank expectedBank = new Bank();

        when(bankRequest.toBank()).thenReturn(expectedBank);

        Bank bank = bankDao.addBank(bankRequest);

        assertThat(bank, is(expectedBank));

        verify(entityManager).persist(expectedBank);
        verify(entityManagerProvider).get();
    }
}