package dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import controllers.banks.BankRequest;
import models.banks.Bank;
import models.banks.BankCollection;
import ninja.jpa.UnitOfWork;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class BankDao {

    @Inject
    Provider<EntityManager> entityManagerProvider;

    @UnitOfWork
    public BankCollection banks() {

        EntityManager entityManager = entityManagerProvider.get();

        TypedQuery<Bank> banksQuery = entityManager.createQuery("SELECT b FROM Bank b", Bank.class);
        List<Bank> resultList = banksQuery.getResultList();

        return new BankCollection(resultList);
    }

    @UnitOfWork
    public BankCollection getBank(Long id) {

        EntityManager entityManager = entityManagerProvider.get();

        TypedQuery<Bank> bankQuery = entityManager.createQuery("SELECT b FROM Bank b WHERE b.id = :bankId", Bank.class);
        List<Bank> resultList = bankQuery.setParameter("bankId", id).getResultList();

        return new BankCollection(resultList);
    }

    @Transactional
    public Bank addBank(BankRequest bankRequest) {

        EntityManager entityManager = entityManagerProvider.get();

        Bank bank = bankRequest.toBank();
        entityManager.persist(bank);

        return bank;
    }
}
