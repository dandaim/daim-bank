package controllers.customers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dao.CustomerDao;
import models.customers.CustomerCollection;
import ninja.Result;
import ninja.Results;

@Singleton
public class CustomerController {

    @Inject
    private CustomerDao customerDao;

    public Result getCustomers() {

        //TODO test
        CustomerCollection customers = customerDao.customers();

        return  Results.json().render(customers.data());
    }
}
