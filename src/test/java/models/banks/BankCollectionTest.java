package models.banks;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class BankCollectionTest {

    @Test
    public void returnFalseWhenBankListIsEmpty() {

        BankCollection bankCollection = new BankCollection(Collections.emptyList());

        assertFalse(bankCollection.isNotEmpty());
    }

    @Test
    public void returnTrueWhenBankListIsNotEmpty() {

        List<Bank> banks = Collections.singletonList(mock(Bank.class));
        BankCollection bankCollection = new BankCollection(banks);

        assertTrue(bankCollection.isNotEmpty());
    }

    @Test
    public void returnFirstBankAsSingleResult() {

        Bank bank1 = mock(Bank.class);
        Bank bank2 = mock(Bank.class);

        List<Bank> banks = Arrays.asList(bank1, bank2);
        BankCollection bankCollection = new BankCollection(banks);

        assertThat(bankCollection.singleResult(), is(bank1));
    }
}