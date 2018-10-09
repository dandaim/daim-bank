/**
 * Copyright (C) 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package conf;

import com.google.inject.Inject;
import controllers.accounts.AccountController;
import controllers.banks.BankController;
import controllers.branches.BranchController;
import controllers.customers.CustomerController;
import controllers.transactions.TransactionController;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import ninja.utils.NinjaProperties;

public class Routes implements ApplicationRoutes {

    @Inject
    NinjaProperties ninjaProperties;

    /**
     * Using a (almost) nice DSL we can configure the router.
     * 
     * The second argument NinjaModuleDemoRouter contains all routes of a
     * submodule. By simply injecting it we activate the routes.
     * 
     * @param router
     *            The default router of this application
     */
    @Override
    public void init(Router router) {

        router.GET().route("/banks").with(BankController::getBanks);
        router.GET().route("/banks/{id}").with(BankController::getBank);
        router.GET().route("/banks/{id}/branches").with(BankController::getBranches);
        router.POST().route("/banks").with(BankController::createBank);

        router.GET().route("/branches").with(BranchController::getBranches);

        router.GET().route("/accounts").with(AccountController::getAccounts);

        router.GET().route("/customers").with(CustomerController::getCustomers);

        router.POST().route("/transactions").with(TransactionController::add);
        router.GET().route("/transactions").with(TransactionController::getTransactions);
    }

}
