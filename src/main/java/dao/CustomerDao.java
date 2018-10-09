package dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import models.customers.Customer;
import models.customers.CustomerCollection;
import ninja.jpa.UnitOfWork;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CustomerDao {

    @Inject
    Provider<EntityManager> entityManagerProvider;

    @UnitOfWork
    public CustomerCollection customers() {

        EntityManager entityManager = entityManagerProvider.get();

        TypedQuery<Customer> customerQuery = entityManager.createQuery("SELECT c FROM Customer c", Customer.class);
        List<Customer> customers = customerQuery.getResultList();

        return new CustomerCollection(customers);
    }
}
