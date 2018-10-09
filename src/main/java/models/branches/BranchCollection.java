package models.branches;

import java.util.List;

public class BranchCollection {

    private List<Branch> branches;

    public BranchCollection(List<Branch> branches) {
        this.branches = branches;
    }

    public List<Branch> data() {
        return this.branches;
    }
}
