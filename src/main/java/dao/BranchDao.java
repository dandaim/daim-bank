package dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import models.banks.Bank;
import models.branches.Branch;
import models.branches.BranchCollection;
import ninja.jpa.UnitOfWork;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class BranchDao {

    @Inject
    Provider<EntityManager> entityManagerProvider;

    @UnitOfWork
    public BranchCollection branches() {

        EntityManager entityManager = entityManagerProvider.get();

        TypedQuery<Branch> branchQuery = entityManager.createQuery("SELECT b FROM Branch b", Branch.class);
        List<Branch> resultList = branchQuery.getResultList();

        return new BranchCollection(resultList);
    }

    @UnitOfWork
    public BranchCollection branchesByBank(Bank bank) {

        EntityManager entityManager = entityManagerProvider.get();

        TypedQuery<Branch> branchQuery = entityManager.createQuery("SELECT b FROM Branch b WHERE b.bank.id = :bankId", Branch.class);
        List<Branch> resultList = branchQuery.setParameter("bankId", bank.id()).getResultList();

        return new BranchCollection(resultList);
    }
}
