package controllers.banks;


import dao.BankDao;
import dao.BranchDao;
import models.banks.Bank;
import models.banks.BankCollection;
import models.branches.BranchCollection;
import ninja.Result;
import ninja.validation.Validation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BankControllerTest {

    @Mock
    private BankDao bankDao;

    @Mock
    private BranchDao branchDao;

    @InjectMocks
    private BankController bankController;

    @Test
    public void callBankDaoToGetBanksList() {

        when(bankDao.banks()).thenReturn(mock(BankCollection.class));

        Result result = bankController.getBanks();

        verify(bankDao).banks();

        assertThat(result.getStatusCode(), is(200));
    }

    @Test
    public void returnNotFoundWhenBankDoesNotExist() {
        Long bankId = 1L;

        when(bankDao.getBank(bankId)).thenReturn(mock(BankCollection.class));

        Result result = bankController.getBank(bankId);

        assertThat(result.getStatusCode(), is(404));
    }

    @Test
    public void returnOkWhenBankExists() {
        Long bankId = 1L;

        BankCollection bankCollection = mock(BankCollection.class);

        when(bankCollection.isNotEmpty()).thenReturn(true);
        when(bankDao.getBank(bankId)).thenReturn(bankCollection);

        Result result = bankController.getBank(bankId);

        assertThat(result.getStatusCode(), is(200));
    }

    @Test
    public void returnNotFoundWhenBankDoesNotExistWhenGettingBranches() {
        Long bankId = 1L;

        when(bankDao.getBank(bankId)).thenReturn(mock(BankCollection.class));

        Result result = bankController.getBranches(bankId);

        assertThat(result.getStatusCode(), is(404));
    }

    @Test
    public void returnOkWhenBankExistsAndHaveBranchesAssociated() {
        Long bankId = 1L;

        BankCollection bankCollection = mock(BankCollection.class);
        Bank bank = mock(Bank.class);

        when(bankCollection.singleResult()).thenReturn(bank);
        when(bankCollection.isNotEmpty()).thenReturn(true);
        when(bankDao.getBank(bankId)).thenReturn(bankCollection);
        when(branchDao.branchesByBank(bank)).thenReturn(mock(BranchCollection.class));

        Result result = bankController.getBranches(bankId);

        assertThat(result.getStatusCode(), is(200));
    }

    @Test
    public void returnBadRequestWhenBankRequestValidationFails() {

        BankRequest bankRequest = mock(BankRequest.class);
        Validation validation = mock(Validation.class);

        when(validation.hasViolations()).thenReturn(true);

        Result result = bankController.createBank(bankRequest, validation);

        assertThat(result.getStatusCode(), is(400));
    }

    @Test
    public void returnCreatedWhenBankIsCreatedSuccessfully() {

        BankRequest bankRequest = mock(BankRequest.class);
        Validation validation = mock(Validation.class);
        Bank bank = mock(Bank.class);

        when(bankDao.addBank(bankRequest)).thenReturn(bank);

        Result result = bankController.createBank(bankRequest, validation);

        assertThat(result.getStatusCode(), is(201));
    }
}