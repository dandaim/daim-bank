package controllers.branches;

import com.google.inject.Singleton;
import dao.BranchDao;
import models.branches.BranchCollection;
import ninja.Result;
import ninja.Results;

import javax.inject.Inject;

@Singleton
public class BranchController {

    @Inject
    private BranchDao branchDao;

    public Result getBranches() {

        BranchCollection branches = branchDao.branches();

        return  Results.json().render(branches.data());
    }
}
