package dao;

import com.google.inject.Provider;
import models.banks.Bank;
import models.banks.BankCollection;
import models.customers.Customer;
import models.customers.CustomerCollection;
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
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerDaoTest {

    @InjectMocks
    private CustomerDao customerDao;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Provider<EntityManager> entityManagerProvider;

    @Mock
    private TypedQuery<Customer> query;

    @Before
    public void setUp() {
        when(entityManagerProvider.get()).thenReturn(entityManager);
    }

    @Test
    public void returnCustomerCollectionFromDatabase() {
        List<Customer> customers = Collections.emptyList();

        when(entityManager.createQuery("SELECT c FROM Customer c", Customer.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(customers);

        CustomerCollection customerCollection = customerDao.customers();

        assertThat(customerCollection.data(), is(customers));
        verify(entityManagerProvider).get();

    }
}