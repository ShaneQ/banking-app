package ie.shanequaid.banking.problem;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class AccountTypeProblem extends AbstractThrowableProblem {

    public AccountTypeProblem() {
        super(null, "account type not recognised", Status.BAD_REQUEST);
    }

}