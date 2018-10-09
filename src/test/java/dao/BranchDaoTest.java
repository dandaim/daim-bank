package dao;

import com.google.inject.Provider;
import models.banks.Bank;
import models.banks.BankCollection;
import models.branches.Branch;
import models.branches.BranchCollection;
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
public class BranchDaoTest {

    @Mock
    private Provider<EntityManager> entityManagerProvider;

    @InjectMocks
    private BranchDao branchDao;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Branch> query;

    @Before
    public void setUp() {
        when(entityManagerProvider.get()).thenReturn(entityManager);
    }

    @Test
    public void returnBranchCollectionFromDatabase() {
        List<Branch> branches = Collections.emptyList();

        when(entityManager.createQuery("SELECT b FROM Branch b", Branch.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(branches);

        BranchCollection branchCollection = branchDao.branches();

        assertThat(branchCollection.data(), is(branches));

        verify(entityManagerProvider).get();
    }

    @Test
    public void returnBranchesByBankId() {

        Long bankId = 1L;
        Bank bank = mock(Bank.class);
        List<Branch> branches = Collections.emptyList();

        when(bank.id()).thenReturn(bankId);
        when(entityManager.createQuery("SELECT b FROM Branch b WHERE b.bank.id = :bankId", Branch.class)).thenReturn(query);
        when(query.setParameter("bankId", bankId)).thenReturn(query);
        when(query.getResultList()).thenReturn(branches);

        BranchCollection branchCollection = branchDao.branchesByBank(bank);

        assertThat(branchCollection.data(), is(branches));

        verify(entityManagerProvider).get();
    }

}