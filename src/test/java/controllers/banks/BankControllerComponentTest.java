package controllers.banks;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.banks.Bank;
import ninja.NinjaTest;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.junit.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class BankControllerComponentTest extends NinjaTest {

    @Test
    public void createNewBank() throws Exception {

        String url = getServerAddress() + "/banks";

        Map<String, String> headers = new HashMap<>();
        Map<String, String> form = new HashMap<>();
        form.put("name", "My Beautiful bank");

        String response = ninjaTestBrowser.makePostRequestWithFormParameters(url, headers, form);

        Bank bank = new ObjectMapper().readValue(response, Bank.class);

        assertThat(bank.id(), notNullValue());
        assertThat(bank.name(), is("My Beautiful bank"));
    }

    @Test
    public void getBanks() throws Exception {

        String url = getServerAddress() + "/banks";

        Map<String, String> headers = new HashMap<>();

        HttpResponse httpResponse = ninjaTestBrowser.makeRequestAndGetResponse(url, headers);

        InputStream content = httpResponse.getEntity().getContent();
        String response = IOUtils.toString(content, StandardCharsets.UTF_8);
        List<Bank> banks = new ObjectMapper().readValue(response, List.class);

        assertThat(httpResponse.getStatusLine().getStatusCode(), is(200));
        assertThat(banks.size(), is(1));
    }

    @Test
    public void getBankById() throws Exception {

        String url = getServerAddress() + "/banks/1";

        Map<String, String> headers = new HashMap<>();

        HttpResponse httpResponse = ninjaTestBrowser.makeRequestAndGetResponse(url, headers);

        InputStream content = httpResponse.getEntity().getContent();
        String response = IOUtils.toString(content, StandardCharsets.UTF_8);
        Bank bank = new ObjectMapper().readValue(response, Bank.class);

        assertThat(httpResponse.getStatusLine().getStatusCode(), is(200));
        assertThat(bank.id(), is(1L));
        assertThat(bank.name(), is("Daim's Bank"));
    }


}
