package controllers.transactions;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dao.AccountDao;
import dao.TransactionDao;
import models.accounts.Account;
import models.accounts.AccountCollection;
import models.transactions.Transaction;
import models.transactions.TransactionCollection;
import ninja.Result;
import ninja.Results;
import org.eclipse.jetty.http.HttpStatus;

@Singleton
public class TransactionController {

    @Inject
    private TransactionDao transactionDao;

    @Inject
    private AccountDao accountDao;

    public Result add(TransactionRequest transactionRequest) {

        AccountCollection accountCollectionFrom = accountDao.getAccount(transactionRequest.accountFrom());
        AccountCollection accountCollectionTo = accountDao.getAccount(transactionRequest.accountTo());

        if (accountCollectionFrom.isEmpty() || accountCollectionTo.isEmpty()) {
            return Results
                    .badRequest()
                    .json()
                    .render(new TransactionError("One of the accounts does not exist."));
        }

        Account accountFrom = accountCollectionFrom.singleResult();
        Account accountTo = accountCollectionTo.singleResult();

        Transaction transaction = new Transaction(accountFrom,
                accountCollectionTo.singleResult(),
                transactionRequest.amount(),
                transactionRequest.type());

        if (transaction.hasInsufficientFunds()) {
            return Results
                    .badRequest()
                    .json()
                    .render(new TransactionError("Account has insufficient funds to make this transaction."));
        }


        Transaction persistedTransaction = transactionDao.addTransaction(transaction);

        accountFrom.debitBalance(transaction.amount());
        accountDao.update(accountFrom);

        accountTo.addBalance(transaction.amount());
        accountDao.update(accountTo);

        return Results.status(HttpStatus.CREATED_201).json().render(persistedTransaction);
    }

    public Result getTransactions() {

        TransactionCollection transactionCollection = transactionDao.getTransactions();

        return Results.ok().json().render(transactionCollection.data());
    }

}
