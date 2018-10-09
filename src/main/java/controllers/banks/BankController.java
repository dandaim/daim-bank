package controllers.banks;

import dao.BankDao;
import dao.BranchDao;
import models.banks.Bank;
import models.banks.BankCollection;
import models.branches.BranchCollection;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;
import org.eclipse.jetty.http.HttpStatus;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BankController {

    @Inject
    private BankDao bankDao;

    @Inject
    private BranchDao branchDao;

    public Result getBanks() {

        BankCollection banks = bankDao.banks();

        return Results.json().render(banks.data());
    }

    public Result getBank(@PathParam("id") Long id) {

        BankCollection bankCollection = bankDao.getBank(id);

        if (bankCollection.isNotEmpty()) {
            return Results.json().render(bankCollection.singleResult());
        }

        return Results.notFound().render(Result.NO_HTTP_BODY);
    }

    public Result getBranches(@PathParam("id") Long id) {
        BankCollection bankCollection = bankDao.getBank(id);

        if (bankCollection.isNotEmpty()) {
            BranchCollection branchCollection = branchDao.branchesByBank(bankCollection.singleResult());

            return Results.json().render(branchCollection.data());
        }

        return Results.notFound().render(Result.NO_HTTP_BODY);
    }

    public Result createBank(@JSR303Validation BankRequest bankRequest,
                             Validation validation) {


        if (validation.hasViolations()) {
            return Results
                    .status(HttpStatus.BAD_REQUEST_400)
                    .json()
                    .render(validation.getViolations());
        }

        Bank bank = bankDao.addBank(bankRequest);

        return Results
                .status(HttpStatus.CREATED_201)
                .json()
                .render(bank);
    }
}
