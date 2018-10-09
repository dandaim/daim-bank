package dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import models.accounts.Account;
import models.accounts.AccountCollection;
import ninja.jpa.UnitOfWork;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class AccountDao {

    @Inject
    Provider<EntityManager> entityManagerProvider;

    @UnitOfWork
    public AccountCollection getAccount(Long accountId) {

        EntityManager entityManager = entityManagerProvider.get();

        TypedQuery<Account> accountsQuery = entityManager.createQuery("SELECT a FROM Account a WHERE a.id = :accountId", Account.class);
        accountsQuery.setParameter("accountId", accountId);
        List<Account> resultList = accountsQuery.getResultList();

        return new AccountCollection(resultList);
    }

    @Transactional
    public void update(Account account) {
        EntityManager entityManager = entityManagerProvider.get();
        Query query = entityManager.createQuery("UPDATE Account SET currentBalance = :currentBalance WHERE id = :id");

        query.setParameter("currentBalance", account.currentBalance());
        query.setParameter("id", account.id());

        query.executeUpdate();
    }

    @UnitOfWork
    public AccountCollection getAccounts() {

        EntityManager entityManager = entityManagerProvider.get();

        TypedQuery<Account> accountsQuery = entityManager.createQuery("SELECT a FROM Account a", Account.class);
        List<Account> resultList = accountsQuery.getResultList();

        return new AccountCollection(resultList);
    }
}
