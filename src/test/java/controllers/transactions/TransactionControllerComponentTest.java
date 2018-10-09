package controllers.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.transactions.TransactionType;
import ninja.NinjaTest;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class TransactionControllerComponentTest extends NinjaTest {

    @Test
    public void addTransactions() throws Exception {
        String url = getServerAddress() + "/transactions";

        Map<String, String> headers = new HashMap<>();
        Map<String, String> form = new HashMap<>();
        form.put("amount", "10.0");
        form.put("transactionType", "DEPOSIT");
        form.put("accountFromId", "1");
        form.put("accountToId", "2");

        String response = ninjaTestBrowser.makePostRequestWithFormParameters(url, headers, form);

        TransactionResponse transactionResponse = new ObjectMapper().readValue(response, TransactionResponse.class);

        assertThat(transactionResponse.id(), notNullValue());
        assertThat(transactionResponse.accountFromId(), is(1L));
        assertThat(transactionResponse.accountToId(), is(2L));
        assertThat(transactionResponse.amount(), is(10D));
        assertThat(transactionResponse.transactionType(), is(TransactionType.DEPOSIT));
    }
}