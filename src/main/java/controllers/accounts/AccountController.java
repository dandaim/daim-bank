package controllers.accounts;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dao.AccountDao;
import models.accounts.AccountCollection;
import ninja.Result;
import ninja.Results;

@Singleton
public class AccountController {

    @Inject
    private AccountDao accountDao;

    public Result getAccounts() {

        AccountCollection accounts = accountDao.getAccounts();

        return Results.ok().json().render(accounts.data());
    }
}
