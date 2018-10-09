package controllers.transactions;

import dao.AccountDao;
import dao.TransactionDao;
import models.accounts.Account;
import models.accounts.AccountCollection;
import models.transactions.Transaction;
import models.transactions.TransactionCollection;
import models.transactions.TransactionType;
import ninja.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

    private static final Long ACCOUNT_FROM_ID = 1L;
    private static final Long ACCOUNT_TO_ID = 2L;
    private static final Double AMOUNT = 10D;

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private AccountDao accountDao;

    @Mock
    private TransactionDao transactionDao;

    @Test
    public void returnBadRequestWhenAccountFromDoesNotExist() {

        TransactionRequest transactionRequest = buildTransactionRequest();

        AccountCollection emptyAccountCollection = new AccountCollection(Collections.emptyList());
        AccountCollection accountCollection = new AccountCollection(Collections.singletonList(mock(Account.class)));

        when(accountDao.getAccount(ACCOUNT_FROM_ID)).thenReturn(emptyAccountCollection);
        when(accountDao.getAccount(ACCOUNT_TO_ID)).thenReturn(accountCollection);

        Result result = transactionController.add(transactionRequest);

        assertThat(result.getStatusCode(), is(400));
    }

    @Test
    public void returnBadRequestWhenAccountToDoesNotExist() {

        TransactionRequest transactionRequest = buildTransactionRequest();
        Account accountFrom = mock(Account.class);

        AccountCollection emptyAccountCollection = new AccountCollection(Collections.emptyList());
        AccountCollection accountFromCollection = new AccountCollection(Collections.singletonList(accountFrom));

        when(accountDao.getAccount(ACCOUNT_FROM_ID)).thenReturn(accountFromCollection);
        when(accountDao.getAccount(ACCOUNT_TO_ID)).thenReturn(emptyAccountCollection);

        Result result = transactionController.add(transactionRequest);

        assertThat(result.getStatusCode(), is(400));
    }

    @Test
    public void returnBadRequestWhenAccountFromHasInsufficientFunds() {
        TransactionRequest transactionRequest = buildTransactionRequest();
        Account accountFrom = mock(Account.class);
        Account accountTo = mock(Account.class);

        AccountCollection accountToCollection = new AccountCollection(Collections.singletonList(accountTo));
        AccountCollection accountFromCollection = new AccountCollection(Collections.singletonList(accountFrom));

        when(accountDao.getAccount(ACCOUNT_FROM_ID)).thenReturn(accountFromCollection);
        when(accountDao.getAccount(ACCOUNT_TO_ID)).thenReturn(accountToCollection);

        Result result = transactionController.add(transactionRequest);

        assertThat(result.getStatusCode(), is(400));
    }

    @Test
    public void addTransactionToDatabase() {

        TransactionRequest transactionRequest = buildTransactionRequest();
        Account accountFrom = mock(Account.class);
        Account accountTo = mock(Account.class);
        Transaction transaction = mock(Transaction.class);

        AccountCollection accountToCollection = new AccountCollection(Collections.singletonList(accountTo));
        AccountCollection accountFromCollection = new AccountCollection(Collections.singletonList(accountFrom));

        when(accountFrom.currentBalance()).thenReturn(AMOUNT);
        when(accountFrom.id()).thenReturn(ACCOUNT_FROM_ID);
        when(accountTo.id()).thenReturn(ACCOUNT_TO_ID);
        when(accountDao.getAccount(ACCOUNT_FROM_ID)).thenReturn(accountFromCollection);
        when(accountDao.getAccount(ACCOUNT_TO_ID)).thenReturn(accountToCollection);
        when(transactionDao.addTransaction(any(Transaction.class))).thenReturn(transaction);

        Result result = transactionController.add(transactionRequest);

        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);

        verify(transactionDao).addTransaction(captor.capture());
        verify(accountFrom).debitBalance(transactionRequest.amount());
        verify(accountDao).update(accountFrom);
        verify(accountTo).addBalance(transactionRequest.amount());
        verify(accountDao).update(accountTo);

        Transaction capturedTransaction = captor.getValue();

        assertThat(capturedTransaction.accountFromId(), is(ACCOUNT_FROM_ID));
        assertThat(capturedTransaction.accountToId(), is(ACCOUNT_TO_ID));
        assertThat(capturedTransaction.amount(), is(AMOUNT));
        assertThat(capturedTransaction.transactionType(), is(TransactionType.DEPOSIT));
        assertThat(result.getStatusCode(), is(201));
    }

    @Test
    public void returnOkWhenGettingTransactionsList() {

        when(transactionDao.getTransactions()).thenReturn(mock(TransactionCollection.class));

        Result result = transactionController.getTransactions();

        assertThat(result.getStatusCode(), is(200));
    }

    private TransactionRequest buildTransactionRequest() {

        return new TransactionRequest(AMOUNT, TransactionType.DEPOSIT, ACCOUNT_FROM_ID, ACCOUNT_TO_ID);
    }
}