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

package dao;


import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import models.accounts.Account;
import models.accounts.AccountType;
import models.banks.Bank;
import models.branches.Branch;
import models.customers.Customer;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;
import java.util.List;

public class SetupDao {
    
    @Inject
    Provider<EntityManager> entityManagerProvider;

    @Transactional
    public void setup() {
        
        EntityManager entityManager = entityManagerProvider.get();

        TypedQuery<Bank> query = entityManager.createQuery("SELECT b FROM Bank b", Bank.class);
        List<Bank> banks = query.getResultList();

        if (banks.isEmpty()) {
            Bank bank = new Bank("Daim's Bank");
            entityManager.persist(bank);

            Branch branch = new Branch(bank, "Downtown branch");
            entityManager.persist(branch);

            Customer customer = new Customer(branch, "Daniel Daim", "+55119999977777", "danieldaim@email.com");
            entityManager.persist(customer);

            Customer customer2 = new Customer(branch, "Daniel Daim", "+55119999988888", "danieldaim@email.com");
            entityManager.persist(customer2);

            Account account = new Account(branch, customer, true, AccountType.CHECKING, 10000.00);
            entityManager.persist(account);

            Account account2 = new Account(branch, customer2, true, AccountType.CHECKING, 20000.00);
            entityManager.persist(account2);

            entityManager.setFlushMode(FlushModeType.COMMIT);
            entityManager.flush();
        }
    }
}
