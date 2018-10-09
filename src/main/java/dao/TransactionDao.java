package dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import models.transactions.Transaction;
import models.transactions.TransactionCollection;
import ninja.jpa.UnitOfWork;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class TransactionDao {

    @Inject
    Provider<EntityManager> entityManagerProvider;

    @Transactional
    public Transaction addTransaction(Transaction transaction) {

        EntityManager entityManager = entityManagerProvider.get();
        entityManager.persist(transaction);

        return transaction;
    }

    @UnitOfWork
    public TransactionCollection getTransactions() {
        EntityManager entityManager = entityManagerProvider.get();

        TypedQuery<Transaction> transactionQuery = entityManager.createQuery("SELECT t FROM Transaction t", Transaction.class);
        List<Transaction> resultList = transactionQuery.getResultList();

        return new TransactionCollection(resultList);
    }
}
